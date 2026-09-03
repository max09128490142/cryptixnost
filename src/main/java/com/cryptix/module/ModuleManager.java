package com.cryptix.module;

import com.cryptix.module.combat.AimAssist;
import com.cryptix.module.combat.AntiBot;
import com.cryptix.module.combat.AutoClicker;
import com.cryptix.module.combat.Criticals;
import com.cryptix.module.combat.KillAura;
import com.cryptix.module.combat.SprintReset;
import com.cryptix.module.combat.TriggerBot;
import com.cryptix.module.combat.Velocity;
import com.cryptix.module.misc.Disabler;
import com.cryptix.module.misc.Whitelist;
import com.cryptix.module.misc.WindCharge;
import com.cryptix.module.movement.AntiSwim;
import com.cryptix.module.movement.AutoWalk;
import com.cryptix.module.movement.Flight;
import com.cryptix.module.movement.InvMove;
import com.cryptix.module.movement.KeepSprint;
import com.cryptix.module.movement.LongJump;
import com.cryptix.module.movement.MoveFix;
import com.cryptix.module.movement.NoSlow;
import com.cryptix.module.movement.Speed;
import com.cryptix.module.movement.Sprint;
import com.cryptix.module.movement.Stasis;
import com.cryptix.module.movement.Timer;
import com.cryptix.module.player.AutoHead;
import com.cryptix.module.player.AutoTool;
import com.cryptix.module.player.Backtrack;
import com.cryptix.module.player.BedAura;
import com.cryptix.module.player.ChestStealer;
import com.cryptix.module.player.Eagle;
import com.cryptix.module.player.FastMine;
import com.cryptix.module.player.FastPlace;
import com.cryptix.module.player.InvManager;
import com.cryptix.module.player.LagRange;
import com.cryptix.module.player.NoFall;
import com.cryptix.module.player.NoJumpDelay;
import com.cryptix.module.player.Scaffold;
import com.cryptix.module.visual.Ambience;
import com.cryptix.module.visual.Animations;
import com.cryptix.module.visual.AntiFire;
import com.cryptix.module.visual.BedPlates;
import com.cryptix.module.visual.Cape;
import com.cryptix.module.visual.ClickGuiModule;
import com.cryptix.module.visual.FullBright;
import com.cryptix.module.visual.HudModule;
import com.cryptix.module.visual.NameTags;
import com.cryptix.module.visual.NoHurtCam;
import com.cryptix.module.visual.PlayerEsp;
import com.cryptix.module.visual.Scoreboard;
import com.cryptix.module.visual.TargetHudModule;
import com.cryptix.module.visual.ThemeModule;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;

public class ModuleManager {
   public static Velocity f28;
   public static Cape f38;
   public static Scaffold f29;
   public static ThemeModule f24;
   public static AntiBot f27;
   public static KillAura f26;
   public static Scoreboard f32;
   private static List<Module> sortedModules;
   public static AntiFire f31;
   public static BedAura f33;
   public static LongJump f35;
   public static Animations f34;
   public static HudModule f25;
   public static Whitelist f30;
   private static List<Module> modules;
   public static KeepSprint f36;
   public static AntiSwim f37;

   public static void loadEnabled() {
      sortedModules = new ArrayList<>(modules);
      sortModules();
   }

   public static void registerModules() {
      modules = new ArrayList<>();
      modules.add(new AimAssist());
      modules.add(f27 = new AntiBot());
      modules.add(new AutoClicker());
      modules.add(new Criticals());
      modules.add(f26 = new KillAura());
      modules.add(new SprintReset());
      modules.add(new TriggerBot());
      modules.add(f28 = new Velocity());
      modules.add(f37 = new AntiSwim());
      modules.add(new AutoWalk());
      modules.add(new Flight());
      modules.add(new InvMove());
      modules.add(f36 = new KeepSprint());
      modules.add(f35 = new LongJump());
      modules.add(new MoveFix());
      modules.add(new NoSlow());
      modules.add(new Speed());
      modules.add(new Sprint());
      modules.add(new Stasis());
      modules.add(new Timer());
      modules.add(new AutoTool());
      modules.add(new AutoHead());
      modules.add(new Backtrack());
      modules.add(f33 = new BedAura());
      modules.add(new ChestStealer());
      modules.add(new Eagle());
      modules.add(new FastMine());
      modules.add(new FastPlace());
      modules.add(new InvManager());
      modules.add(new LagRange());
      modules.add(new NoFall());
      modules.add(new NoJumpDelay());
      modules.add(f29 = new Scaffold());
      modules.add(new Ambience());
      modules.add(f34 = new Animations());
      modules.add(f31 = new AntiFire());
      modules.add(new BedPlates());
      modules.add(f38 = new Cape());
      modules.add(new ClickGuiModule());
      modules.add(new FullBright());
      modules.add(f25 = new HudModule());
      modules.add(new NameTags());
      modules.add(new NoHurtCam());
      modules.add(new PlayerEsp());
      modules.add(f32 = new Scoreboard());
      modules.add(new TargetHudModule());
      modules.add(f24 = new ThemeModule());
      modules.add(new Disabler());
      modules.add(f30 = new Whitelist());
      modules.add(new WindCharge());
   }

   public static List<Module> m19(Category var0) {
      ArrayList<Module> var1 = new ArrayList<>();

      for (Module var3 : modules) {
         if (var3.getCategory() == var0) {
            var1.add(var3);
         }
      }

      return var1;
   }

   public static void sortModules() {
      sortedModules.sort(
         (var0, var1) -> Integer.compare(Minecraft.getInstance().font.width(var1.getDisplayName()), Minecraft.getInstance().font.width(var0.getDisplayName()))
      );
   }

   public static List<Module> m18() {
      return sortedModules;
   }

   public static List<Module> getModules() {
      return modules;
   }
}
