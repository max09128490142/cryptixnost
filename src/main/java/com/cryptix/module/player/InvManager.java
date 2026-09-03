package com.cryptix.module.player;

import com.cryptix.event.Event;
import com.cryptix.event.Events;
import com.cryptix.module.Category;
import com.cryptix.module.Module;
import com.cryptix.module.ModuleManager;
import com.cryptix.setting.BooleanSetting;
import com.cryptix.setting.NumberSetting;
import java.nio.charset.StandardCharsets;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponents;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.inventory.ContainerInput;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.equipment.Equippable;

public class InvManager extends Module {
   private final NumberSetting f516;
   private final NumberSetting f514;
   private static final String f511 = "Drop Tools";
   private static final String f509 = "Block Slot";
   private static final String f510 = "Gapple Slot";
   private final NumberSetting f513;
   private static final String f512 = "Hypixel";
   private int f519;
   private static final String f507 = "Delay";
   private final BooleanSetting f518;
   private static final String f506 = "InvManager";
   private final BooleanSetting f517;
   private static final String f508 = "Sword Slot";
   private final NumberSetting f515;

   private void pm$90() {
      int var1 = (int)this.f514.m220() - 1;
      if (var1 >= 0 && var1 <= 8) {
         NonNullList var2 = mc.player.getInventory().getNonEquipmentItems();
         int var3 = -1;
         double var4 = -Double.MAX_VALUE;

         for (int var6 = 0; var6 < 36; var6++) {
            ItemStack var7 = mc.player.getInventory().getItem(var6);
            if (!var7.isEmpty() && var7.is(ItemTags.SWORDS)) {
               double var8 = this.pm$92(var7);
               if (var8 > var4) {
                  var4 = var8;
                  var3 = var6;
               }
            }
         }

         for (int var10 = 0; var10 < 36; var10++) {
            if (var10 != var3) {
               ItemStack var12 = (ItemStack)var2.get(var10);
               if (!var12.isEmpty() && var12.is(ItemTags.SWORDS)) {
                  int var13 = var10 < 9 ? var10 + 36 : var10;
                  this.f519 = 0;
                  mc.gameMode.handleContainerInput(mc.player.containerMenu.containerId, var13, 0, ContainerInput.THROW, mc.player);
                  return;
               }
            }
         }

         if (var3 != -1) {
            int var11 = var3 < 9 ? var3 + 36 : var3;
            if (var3 != var1) {
               this.f519 = 0;
               mc.gameMode.handleContainerInput(mc.player.containerMenu.containerId, var11, var1, ContainerInput.SWAP, mc.player);
            }
         }
      }
   }

   private boolean pm$96() {
      NonNullList var1 = mc.player.getInventory().getNonEquipmentItems();

      for (int var2 = 0; var2 < var1.size(); var2++) {
         ItemStack var3 = (ItemStack)var1.get(var2);
         if (!var3.isEmpty() && (var3.is(ItemTags.PICKAXES) || var3.is(ItemTags.AXES) || var3.is(ItemTags.SHOVELS) || var3.is(ItemTags.HOES))) {
            int var4 = var2 < 9 ? var2 + 36 : var2;
            this.f519 = 0;
            mc.gameMode.handleContainerInput(mc.player.containerMenu.containerId, var4, 0, ContainerInput.THROW, mc.player);
            return true;
         }
      }

      return false;
   }

   private void pm$94() {
      for (int var1 = 0; var1 < 4; var1++) {
         int var2 = -1;
         float var3 = -1.0F;

         EquipmentSlot var4 = switch (var1) {
            case 0 -> EquipmentSlot.HEAD;
            case 1 -> EquipmentSlot.CHEST;
            case 2 -> EquipmentSlot.LEGS;
            case 3 -> EquipmentSlot.FEET;
            default -> throw new IllegalStateException();
         };
         NonNullList var5 = mc.player.getInventory().getNonEquipmentItems();

         for (int var6 = 0; var6 < var5.size(); var6++) {
            ItemStack var7 = (ItemStack)var5.get(var6);
            if (!var7.isEmpty()) {
               Equippable var8 = (Equippable)var7.get(DataComponents.EQUIPPABLE);
               if (var8 != null && var8.slot() == var4) {
                  float var9 = this.pm$95(var7);
                  if (var9 > var3) {
                     var3 = var9;
                     var2 = var6;
                  }
               }
            }
         }

         if (var2 != -1) {
            ItemStack var10 = mc.player.getItemBySlot(var4);
            float var11 = this.pm$95(var10);
            if (var10.isEmpty() || !(var11 >= var3)) {
               if (!var10.isEmpty()) {
                  byte var13 = switch (var4) {
                     case HEAD -> 5;
                     case CHEST -> 6;
                     case LEGS -> 7;
                     case FEET -> 8;
                     default -> -1;
                  };
                  this.f519 = 0;
                  mc.gameMode.handleContainerInput(mc.player.containerMenu.containerId, var13, 0, ContainerInput.THROW, mc.player);
                  return;
               } else {
                  int var12 = var2 < 9 ? var2 + 36 : var2;
                  this.f519 = 0;
                  mc.gameMode.handleContainerInput(mc.player.containerMenu.containerId, var12, 0, ContainerInput.QUICK_MOVE, mc.player);
                  return;
               }
            }
         }
      }
   }

   @Override
   public void onEvent(Event var1) {
      if (var1 == Events.f3) {
         if (!(mc.player.containerMenu instanceof ChestMenu)) {
            if (mc.gui.screen() instanceof InventoryScreen) {
               this.pm$89();
            } else if (this.f518.m215() && !ModuleManager.f29.isEnabled() && mc.gui.screen() == null) {
               this.pm$89();
            } else {
               this.f519 = 0;
            }
         }
      }
   }

   private void pm$89() {
      this.f519++;
      double var1 = Math.sqrt(mc.player.getDeltaMovement().x * mc.player.getDeltaMovement().x + mc.player.getDeltaMovement().z * mc.player.getDeltaMovement().z);
      System.out.println(var1);
      if ((double)this.f519 < this.f513.m220() && this.f518.m215()) {
         mc.player.closeContainer();
      } else if (!this.f517.m215() || !this.pm$96()) {
         this.pm$94();
         this.pm$90();
         this.pm$91();
         this.pm$93();
      }
   }

   private float pm$95(ItemStack var1) {
      if (var1.isEmpty()) {
         return 0.0F;
      } else {
         double[] var2 = new double[]{0.0};
         var1.forEachModifier(((Equippable)var1.get(DataComponents.EQUIPPABLE)).slot(), (var1x, var2x) -> {
            if (var1x.equals(Attributes.ARMOR)) {
               var2[0] += var2x.amount();
            }
         });
         return (float)var2[0];
      }
   }

   private void pm$91() {
      int var1 = (int)this.f515.m220() - 1;
      if (var1 >= 0 && var1 <= 8) {
         NonNullList var2 = mc.player.getInventory().getNonEquipmentItems();
         int var3 = -1;
         int var4 = 0;

         for (int var5 = 0; var5 < var2.size(); var5++) {
            ItemStack var6 = (ItemStack)var2.get(var5);
            if (!var6.isEmpty() && var6.getItem() instanceof BlockItem && var6.getCount() > var4) {
               var4 = var6.getCount();
               var3 = var5;
            }
         }

         if (var3 != -1) {
            if (var3 != var1) {
               int var7 = var3 < 9 ? var3 + 36 : var3;
               this.f519 = 0;
               mc.gameMode.handleContainerInput(mc.player.containerMenu.containerId, var7, var1, ContainerInput.SWAP, mc.player);
            }
         }
      }
   }

   public InvManager() {
      super(f506, Category.PLAYER);
      this.f513 = new NumberSetting(f507, this, 2.0, 0.0, 4.0, 1.0);
      this.f514 = new NumberSetting(f508, this, 1.0, 1.0, 9.0, 1.0);
      this.f515 = new NumberSetting(f509, this, 2.0, 1.0, 9.0, 1.0);
      this.f516 = new NumberSetting(f510, this, 3.0, 1.0, 9.0, 1.0);
      this.f517 = new BooleanSetting(f511, this, false);
      this.f518 = new BooleanSetting(f512, this, false);
   }

   private double pm$92(ItemStack var1) {
      double[] var2 = new double[]{0.0};
      var1.forEachModifier(EquipmentSlot.MAINHAND, (var1x, var2x) -> {
         if (var1x.equals(Attributes.ATTACK_DAMAGE)) {
            var2[0] += var2x.amount();
         }
      });
      return var2[0];
   }

   private void pm$93() {
      int var1 = (int)this.f516.m220() - 1;
      if (var1 >= 0 && var1 <= 8) {
         NonNullList var2 = mc.player.getInventory().getNonEquipmentItems();

         for (int var3 = 0; var3 < var2.size(); var3++) {
            ItemStack var4 = (ItemStack)var2.get(var3);
            if (!var4.isEmpty() && var4.is(Items.GOLDEN_APPLE) && var3 != var1) {
               int var5 = var3 < 9 ? var3 + 36 : var3;
               this.f519 = 0;
               mc.gameMode.handleContainerInput(mc.player.containerMenu.containerId, var5, var1, ContainerInput.SWAP, mc.player);
               return;
            }
         }
      }
   }
}
