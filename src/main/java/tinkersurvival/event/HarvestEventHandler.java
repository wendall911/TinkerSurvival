package tinkersurvival.event;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.block.Block;
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

import tinkersurvival.client.sound.Sounds;
import tinkersurvival.config.Config;
import tinkersurvival.tools.tool.CrudeKnife;
import tinkersurvival.tools.tool.Knife;
import tinkersurvival.util.Chat;
import tinkersurvival.util.ItemUse;
import tinkersurvival.world.block.BlockRock;
import tinkersurvival.world.TinkerSurvivalWorld;

import slimeknights.tconstruct.library.utils.ToolHelper;


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

        // Get Variables for the block and item held
        Block block = event.getState().getBlock();
        ItemStack heldItemStack = player.getHeldItemMainhand();

        // Always allow certian blocks to break at normal speed
        if (block == Blocks.AIR || block == Blocks.LEAVES || block == Blocks.SAND || block == Blocks.GRAVEL) {
            return;
        }

        // Get variables for the required and current harvest levels + tools
        int neededHarvestLevel = block.getHarvestLevel(event.getState());
        String neededToolClass = block.getHarvestTool(event.getState());

        // Allows knifes to break at normal speeds
        if (heldItemStack.getItem() instanceof CrudeKnife) {
            CrudeKnife knife = (CrudeKnife) heldItemStack.getItem();
            if (knife.shouldBreakBlock(block)) {
                return;
            }
        }

        if (heldItemStack.getItem() instanceof Knife) {
            Knife knife = (Knife) heldItemStack.getItem();
            if (knife.shouldBreakBlock(block)) {
                return;
            }
        }

        if (neededToolClass != null) {
            if (ItemUse.isWhitelistItem(heldItemStack)) {
                String toolClass = ItemUse.getToolClass(heldItemStack);

                if (neededToolClass.equals(toolClass)) {
                    if (heldItemStack.getItem().getHarvestLevel(heldItemStack, toolClass, null, null) >= neededHarvestLevel) {
                        return;
                    }
                }
                else if (neededToolClass.equals("axe") && toolClass.equals("mattock")) {
                    return;
                }
                else if (neededToolClass.equals("shovel") && toolClass.equals("mattock")) {
                    return;
                } else if(neededToolClass.equals("shovel") && toolClass.equals("pickaxe") && heldItemStack.getItem().getHarvestLevel(heldItemStack, toolClass, null, null) >= 1) {
                    return;
                }

                switch (neededToolClass) {
                    case "axe":
                        event.setNewSpeed(event.getOriginalSpeed() / 5);
                        break;
                    case "shovel":
                        event.setNewSpeed(event.getOriginalSpeed() / 3);
                        break;
                    case "pickaxe":
                        event.setNewSpeed(event.getOriginalSpeed() / 8);
                    default:
                        event.setNewSpeed(event.getOriginalSpeed() / 3);
                }
            }
            else {
                event.setNewSpeed(event.getOriginalSpeed() / 10);
            }
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

            // Get Variables for the block and item held
            Block block = event.getState().getBlock();
            String blockName = block.getRegistryName().getResourceDomain() + ":" + block.getRegistryName().getResourcePath();
            ItemStack heldItemStack = player.getHeldItemMainhand();

            if (block == Blocks.AIR
                    || block == Blocks.MOB_SPAWNER
                    || block == Blocks.CHEST
                    || block == Blocks.LEAVES
                    || block == Blocks.SAND
                    || block == Blocks.GRASS
                    || block == Blocks.DIRT
                    || block == Blocks.GRAVEL) {
                return;
            }

            // Final case: Get variables for the required and current harvest levels + tools
            int neededHarvestLevel = block.getHarvestLevel(event.getState());
            String neededToolClass = block.getHarvestTool(event.getState());

            if (neededToolClass != null) {
                if (ItemUse.isWhitelistItem(heldItemStack)) {
                    String toolClass = ItemUse.getToolClass(heldItemStack);

                    if (neededToolClass.equals(toolClass)) {
                        if (heldItemStack.getItem().getHarvestLevel(heldItemStack, toolClass, null, null) >= neededHarvestLevel) {
                            return;
                        }
                    }
                    else if (neededToolClass.equals("axe") && toolClass.equals("mattock")) {
                        return;
                    }
                    else if (neededToolClass.equals("shovel") && toolClass.equals("mattock")) {
                        return;
                    }
                    // Metal Pickaxes and above are allowed to function as shovels with no mining speed penalty + block drops.
                    else if (neededToolClass.equals("shovel") && toolClass.equals("pickaxe") && heldItemStack.getItem().getHarvestLevel(heldItemStack, toolClass, null, null) >= 1) {
                        return;
                    }

                    if (!harvestAttempts.containsKey(player)
                            || harvestAttempts.get(player) == null
                            || !harvestAttempts.get(player).equals(pos)) {
                        harvestAttempts.put(player, pos);
                        Chat.sendMessage(player, "message.wrong_tool", neededToolClass);
                    }
                    else {
                        Chat.sendMessage(player, "message.wrong_tool2", neededToolClass);
                        player.attackEntityFrom(DamageSource.GENERIC, 0.01f);
                    }
                }
                else {
                    //Play fail sound
                    Sounds.play(player, Sounds.TOOL_FAIL, 0.6F, 1.0F);
                }
                event.setCanceled(true);
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
            }

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
