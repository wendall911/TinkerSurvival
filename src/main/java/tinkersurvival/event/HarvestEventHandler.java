package tinkersurvival.event;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockDoublePlant;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.SoundCategory;

import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import slimeknights.tconstruct.library.utils.ToolHelper;

import tinkersurvival.client.sound.Sounds;
import tinkersurvival.config.Config;
import tinkersurvival.tools.tool.CrudeHatchet;
import tinkersurvival.tools.tool.CrudeKnife;
import tinkersurvival.tools.tool.CrudeSaw;
import tinkersurvival.tools.tool.Knife;
import tinkersurvival.util.Chat;
import tinkersurvival.util.ItemUse;
import tinkersurvival.world.block.BlockRock;
import tinkersurvival.world.TinkerSurvivalWorld;

public class HarvestEventHandler {
    public static final Map<EntityPlayer, BlockPos> harvestAttempts = new HashMap<>();

    // Controls the slow mining speed of blocks that aren't the right tool
    @SubscribeEvent
    public void slowMining(PlayerEvent.BreakSpeed event) {
        EntityPlayer player = event.getEntityPlayer();

        if (player == null
                || player instanceof FakePlayer
                || player.capabilities.isCreativeMode
        ) {
            return;
        }

        Block block = event.getState().getBlock();

        // Always allow certain blocks to break at normal speed
        if (block == Blocks.AIR || block == Blocks.LEAVES || block == Blocks.SAND || block == Blocks.GRAVEL || block == Blocks.SNOW_LAYER
                || !Config.Features.SLOW_DOWN_DIRT_PUNCHING && (block == Blocks.DIRT || block == Blocks.GRASS)) {
            return;
        }

        int neededHarvestLevel = block.getHarvestLevel(event.getState());
        String neededToolClass = block.getHarvestTool(event.getState());

        float mainHandSpeed = getNewToolSpeed(player.getHeldItemMainhand(), block, neededHarvestLevel, neededToolClass);
        float offHandSpeed = getNewToolSpeed(player.getHeldItemOffhand(), block, neededHarvestLevel, neededToolClass);

        event.setNewSpeed(event.getOriginalSpeed() * Math.max(mainHandSpeed, offHandSpeed));
    }

    private float getNewToolSpeed(ItemStack heldItemStack, Block block, int neededHarvestLevel, String neededToolClass) {
        // Allows knifes to break at normal speeds
        if (heldItemStack.getItem() instanceof CrudeKnife) {
            CrudeKnife knife = (CrudeKnife) heldItemStack.getItem();
            if (knife.shouldBreakBlock(block)) {
                return 1.0F;
            }
        }

        if (heldItemStack.getItem() instanceof Knife) {
            Knife knife = (Knife) heldItemStack.getItem();
            if (knife.shouldBreakBlock(block)) {
                return 1.0F;
            }
        }

        if (neededToolClass != null) {
            String toolClass = ItemUse.getToolClass(heldItemStack);

            if (ItemUse.isWhitelistItem(heldItemStack) && toolClass != null) {
                if (isRightTool(heldItemStack, neededHarvestLevel, neededToolClass, toolClass)) {
                    return 1.0F;
                }

                switch (neededToolClass) {
                    case "axe":
                        return 0.2F / Config.Balance.SLOW_DOWN_MULTIPLIER;
                    case "pickaxe":
                        return 0.125F / Config.Balance.SLOW_DOWN_MULTIPLIER;
                    case "shovel":
                    default:
                        return 0.33F / Config.Balance.SLOW_DOWN_MULTIPLIER;
                }
            } else {
                return 0.1F;
            }
        }

        return 1.0F;
    }

    private boolean isRightTool(ItemStack heldItemStack, int neededHarvestLevel, String neededToolClass, String toolClass) {
        if (neededToolClass.equals(toolClass)) {
            return heldItemStack.getItem().getHarvestLevel(
                    heldItemStack, toolClass, null, null) >= neededHarvestLevel;
        } else if (neededToolClass.equals("axe") && toolClass.equals("mattock")) {
            return true;
        } else if (neededToolClass.equals("shovel") && toolClass.equals("mattock")) {
            return true;
        } else {
            return neededToolClass.equals("shovel") && toolClass.equals("pickaxe")
                    && heldItemStack.getItem().getHarvestLevel(
                        heldItemStack, toolClass, null, null) >= 1;
        }
    }

    // Controls what tool is used for block breaking
    @SubscribeEvent
    public void breakBlock(BlockEvent.BreakEvent event) {
        if (!event.getWorld().isRemote) {
            EntityPlayer player = event.getPlayer();
            BlockPos pos = event.getPos();

            // Explosions, Quarries, etc. all are ok to break blocks
            if (player == null || player instanceof FakePlayer) {
                return;
            }
            // Always allow creative mode
            if (player.capabilities.isCreativeMode) {
                return;
            }

            Block block = event.getState().getBlock();

            if (block == Blocks.AIR
                    || block == Blocks.MOB_SPAWNER
                    || block == Blocks.CHEST
                    || block == Blocks.LEAVES
                    || block == Blocks.SAND
                    || block == Blocks.GRASS
                    || block == Blocks.DIRT
                    || block == Blocks.GRAVEL
                    || block == Blocks.SNOW_LAYER) {
                return;
            }

            String blockName = block.getRegistryName().getNamespace() + ":" + block.getRegistryName().getPath();

            int neededHarvestLevel = block.getHarvestLevel(event.getState());
            String neededToolClass = block.getHarvestTool(event.getState());

            if (neededToolClass != null) {

                ItemStack mainhandItemStack = player.getHeldItemMainhand();
                String mainhandToolClass = ItemUse.getToolClass(mainhandItemStack);

                if (ItemUse.isWhitelistItem(mainhandItemStack) && mainhandToolClass != null) {
                    if (isRightTool(mainhandItemStack, neededHarvestLevel, neededToolClass, mainhandToolClass)) {
                        return;
                    }
                }

                ItemStack offhandItemStack = player.getHeldItemOffhand();
                String offhandToolClass = ItemUse.getToolClass(offhandItemStack);

                if (ItemUse.isWhitelistItem(offhandItemStack) && offhandToolClass != null) {

                    if (isRightTool(offhandItemStack, neededHarvestLevel, neededToolClass, offhandToolClass)) {
                        return;
                    }
                }

                event.setCanceled(true);

                if (!ItemUse.isWhitelistItem(mainhandItemStack) && !ItemUse.isWhitelistItem(offhandItemStack)) {
                    Sounds.play(player, Sounds.TOOL_FAIL, 0.6F, 1.0F);
                    return;
                }

                if (!harvestAttempts.containsKey(player)
                        || harvestAttempts.get(player) == null
                        || !harvestAttempts.get(player).equals(pos)) {

                    harvestAttempts.put(player, pos);
                    Chat.sendMessage(player, "message.wrong_tool", neededToolClass);

                } else {
                    Chat.sendMessage(player, "message.wrong_tool2", neededToolClass);
                    player.attackEntityFrom(DamageSource.GENERIC, 0.01f);
                }
            }
        }
    }

    // Controls the drops of any block that is broken
    @SubscribeEvent
    public void harvestBlock(BlockEvent.HarvestDropsEvent event) {
        EntityPlayer player = event.getHarvester();

        if (!event.getWorld().isRemote) {
            // Explosions, Quarries, etc. all are ok to break blocks
            if (player == null || player instanceof FakePlayer) {
                return;
            }

            // Always allow creative mode
            if (player.capabilities.isCreativeMode) {
                return;
            }

            // Get Variables for the block and item held
            Block block = event.getState().getBlock();
            ItemStack heldItemStack = player.getHeldItemMainhand();

            // Leaves now drop sticks 20% without a knife. 50% with a knife
            if (block instanceof BlockLeaves) {
                float stickDropChance = 0.2F;
                if (heldItemStack.getItem() instanceof CrudeKnife
                        || heldItemStack.getItem() instanceof Knife) {
                    stickDropChance += 0.3F;
                }
                if (event.getWorld().rand.nextFloat() <= stickDropChance) {
                    event.getDrops().add(new ItemStack(Items.STICK));
                }
                return;
            }

            // Allows Knifes to have special drops when breaking blocks
            if (heldItemStack.getItem() instanceof CrudeKnife) {
                CrudeKnife knife = (CrudeKnife) heldItemStack.getItem();
                if (knife.shouldBreakBlock(block)) {
                    if (block instanceof BlockDoublePlant || block instanceof BlockTallGrass) {
                        if (Math.random() < Config.Balance.GRASS_FIBER_CHANCE) {
                            event.getDrops().add(new ItemStack(TinkerSurvivalWorld.grassFiber, 1, 0));
                        }
                    }
                }
                if (knife.shouldDamageItem(block)) {
                    player.getHeldItemMainhand().damageItem(1, player);
                }
                return;
            }

            // Leaves now drop sticks 20% without a knife. 50% with a knife
            if (heldItemStack.getItem() instanceof Knife) {
                Knife knife = (Knife) heldItemStack.getItem();
                if (ToolHelper.isBroken(heldItemStack)) {
                    return;
                }
                if (knife.shouldBreakBlock(block)) {
                    if (block instanceof BlockDoublePlant || block instanceof BlockTallGrass) {
                        if (Math.random() < Config.Balance.GRASS_FIBER_CHANCE + 0.4D) {
                            event.getDrops().add(new ItemStack(TinkerSurvivalWorld.grassFiber, 1, 0));
                        }
                    }
                }
                if (knife.shouldDamageItem(block)) {
                    player.getHeldItemMainhand().damageItem(1, player);
                }

                return;
            }

            // rock drop chance
            if (Config.Balance.ENABLE_ROCK_FROM_DIRT
                    && (block instanceof BlockDirt || block instanceof BlockGrass)) {

                // check if the dirt is searched by hand - to avoid conflicts and ease usage,
                // this checks if the held item is a shovel or similar and drops rock otherwise
                String toolClass = ItemUse.getToolClass(heldItemStack);
                if (toolClass != null) {
                    switch (toolClass) {
                        case "shovel":
                        case "pickaxe":
                        case "axe":
                        case "mattock":
                            // do not drop rocks
                            return;
                    }
                }
                Item held = heldItemStack.getItem();
                if (held instanceof CrudeKnife || held instanceof CrudeHatchet || held instanceof CrudeSaw) {
                    // do not drop rocks
                    return;
                }

                double rockDropChance = Config.Balance.ROCK_FROM_DIRT_CHANCE;
                if (event.getWorld().rand.nextFloat() <= rockDropChance) {
                    event.getDrops().add(new ItemStack(TinkerSurvivalWorld.rockStone));
                }
            }
        }
    }

    @SubscribeEvent
    public void harvestBlockInitialCheck(PlayerEvent.HarvestCheck event) {
        Block block = event.getTargetBlock().getBlock();
        if (block instanceof BlockRock) {
            event.setCanHarvest(true);
        }
    }
}
