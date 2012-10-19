 package me.ThaH3lper.EpicBoss;
 
 import org.bukkit.configuration.file.FileConfiguration;
 import org.bukkit.entity.LivingEntity;
 
 public class API
 {
   private EpicBoss plugin;
 
   public API(EpicBoss plugin)
   {
     this.plugin = plugin;
   }
 
   public boolean entityBoss(LivingEntity e) {
     int id = e.getEntityId();
     if (this.plugin.getCustomConfig(this.plugin.file).contains("mobs"))
     {
       if (this.plugin.getCustomConfig(this.plugin.file).contains("mobs." + id))
       {
         return true;
       }
     }
     return false;
   }
 }
