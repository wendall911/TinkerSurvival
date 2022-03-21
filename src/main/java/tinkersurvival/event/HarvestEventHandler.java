package tinkersurvival.event;

import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Containers;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.material.Material;

import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import tinkersurvival.config.ConfigHandler;
import tinkersurvival.TinkerSurvival;
import tinkersurvival.util.TagManager;
import tinkersurvival.world.TinkerSurvivalWorld;

@Mod.EventBusSubscriber(modid = TinkerSurvival.MODID)
public class HarvestEventHandler {

    @SubscribeEvent
    public static void breakBlock(BlockEvent.BreakEvent event) {
        final LevelAccessor level = event.getWorld();
        final BlockPos pos = event.getPos();
        final BlockState state = level.getBlockState(pos);
        final Player player = event.getPlayer();
        final ItemStack tool = player.getMainHandItem();

        /*
        if (player instanceof ServerPlayer) {
            if (TagManager.Items.KNIFE_TOOLS.contains(tool.getItem())) {
                if (TagManager.Blocks.FIBER_PLANTS.contains(state.getBlock())) {
                    if (level.getRandom().nextFloat() < 0.3) {
                        if (level.getRandom().nextFloat() < ConfigHandler.Server.grassFiberBonusChance()) {
                            NonNullList<ItemStack> dropStack =
                                NonNullList.withSize(1, new ItemStack(TinkerSurvivalWorld.PLANT_FIBER.get(), 1));

                            Containers.dropContents(player.getLevel(), pos, dropStack);

                            tool.hurtAndBreak(1, player, (item) -> {
                                item.broadcastBreakEvent(InteractionHand.MAIN_HAND);
                            });
                        }
                    }
                }

                if (state.getMaterial() == Material.LEAVES) {
                    TinkerSurvival.LOGGER.warn("BreakEvent: Leaves!!!!");
                }
            }
        }
        */
    }

	@SubscribeEvent
    public static void harvestCheckEvent(PlayerEvent.HarvestCheck event) {
        /*
        BlockState state = event.getTargetBlock();

        TinkerSurvival.LOGGER.warn(state.getBlock());

        if (state.getMaterial() == Material.LEAVES) {
            TinkerSurvival.LOGGER.warn("Leaves!!!!");
        }
        */
    }

    /*
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
                || !ConfigHandler.features.SLOW_DOWN_DIRT_PUNCHING && (block == Blocks.DIRT || block == Blocks.GRASS)) {
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
            String blockMod = block.getRegistryName().getNamespace();

            if (ItemUse.isWhitelistItem(heldItemStack) && toolClass != null) {
                if (isRightTool(heldItemStack, neededHarvestLevel, neededToolClass, toolClass, blockMod)) {
                    return 1.0F;
                }

                switch (neededToolClass) {
                    case "axe":
                        return 0.2F / ConfigHandler.balance.SLOW_DOWN_MULTIPLIER;
                    case "pickaxe":
                        return 0.125F / ConfigHandler.balance.SLOW_DOWN_MULTIPLIER;
                    case "shovel":
                    default:
                        return 0.33F / ConfigHandler.balance.SLOW_DOWN_MULTIPLIER;
                }
            } else {
                return 0.1F;
            }
        }

        return 1.0F;
    }

    private boolean isRightTool(ItemStack heldItemStack, int neededHarvestLevel, String neededToolClass, String toolClass, String blockMod) {
        if (neededToolClass.equals(toolClass)) {
            return heldItemStack.getItem().getHarvestLevel(
                    heldItemStack, toolClass, null, null) >= neededHarvestLevel;
        } else if (neededToolClass.equals("axe") && toolClass.equals("mattock")) {
            return true;
        } else if (neededToolClass.equals("shovel") && toolClass.equals("mattock")) {
            return true;
        } else if (toolClass.equals("wrench")) {
            return heldItemStack.getItem().getRegistryName().getNamespace().equals(blockMod);
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

            String blockMod = block.getRegistryName().getNamespace();
            int neededHarvestLevel = block.getHarvestLevel(event.getState());
            String neededToolClass = block.getHarvestTool(event.getState());

            if (neededToolClass != null) {

                ItemStack mainhandItemStack = player.getHeldItemMainhand();
                String mainhandToolClass = ItemUse.getToolClass(mainhandItemStack);

                if (ItemUse.isWhitelistItem(mainhandItemStack) && mainhandToolClass != null) {
                    if (isRightTool(mainhandItemStack, neededHarvestLevel, neededToolClass, mainhandToolClass, blockMod)) {
                        return;
                    }
                }

                ItemStack offhandItemStack = player.getHeldItemOffhand();
                String offhandToolClass = ItemUse.getToolClass(offhandItemStack);

                if (ItemUse.isWhitelistItem(offhandItemStack) && offhandToolClass != null) {

                    if (isRightTool(offhandItemStack, neededHarvestLevel, neededToolClass, offhandToolClass, blockMod)) {
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
                float stickDropChance = ConfigHandler.balance.STICK_DROP_CHANCE_HAND;

                if (heldItemStack.getItem() instanceof CrudeKnife
                        || heldItemStack.getItem() instanceof Knife) {
                    stickDropChance = ConfigHandler.balance.STICK_DROP_CHANCE_KNIFE;
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
                        if (Math.random() < ConfigHandler.balance.GRASS_FIBER_CHANCE) {
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
                        if (Math.random() < ConfigHandler.balance.GRASS_FIBER_CHANCE + 0.4D) {
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
            if (ConfigHandler.balance.ENABLE_ROCK_FROM_DIRT
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

                double rockDropChance = ConfigHandler.balance.ROCK_FROM_DIRT_CHANCE;
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
    */
}
