package com.cryptix.util;

import com.mojang.blaze3d.platform.NativeImage;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.resources.Identifier;

/**
 * Uploads PNGs straight out of the mod jar into the texture manager.
 *
 * <p>Minecraft's resource manager only reads the vanilla jar and resource packs; mod assets are
 * mounted by the Fabric API resource loader, which this mod does not depend on. Without it every
 * {@code cryptix:...} texture resolved to the missing-texture (black/magenta) sprite. Registering
 * the images ourselves under the same Identifier makes {@code TextureManager.getTexture} find them
 * before it falls back to the resource manager.
 */
public final class ModTextures {
   private static final Set<Identifier> REGISTERED = new HashSet<>();

   private ModTextures() {
   }

   /** Registers assets/cryptix/&lt;path&gt; under cryptix:&lt;path&gt;, once. Call from the render thread. */
   public static Identifier register(String path) {
      Identifier id = Identifier.fromNamespaceAndPath("cryptix", path);
      if (REGISTERED.add(id)) {
         NativeImage image = read(id);
         if (image != null) {
            Minecraft.getInstance().getTextureManager().register(id, new DynamicTexture(id::toString, image));
         }
      }

      return id;
   }

   /** Decodes assets/&lt;namespace&gt;/&lt;path&gt; from the mod jar, or null when it is missing or broken. */
   public static NativeImage read(Identifier id) {
      String resource = "/assets/" + id.getNamespace() + "/" + id.getPath();

      try (InputStream in = ModTextures.class.getResourceAsStream(resource)) {
         if (in == null) {
            System.out.println("[Cryptix] texture: resource missing " + resource);
            return null;
         }

         return NativeImage.read(in);
      } catch (Exception e) {
         System.out.println("[Cryptix] texture: failed to read " + resource + " (" + e + ")");
         return null;
      }
   }
}
