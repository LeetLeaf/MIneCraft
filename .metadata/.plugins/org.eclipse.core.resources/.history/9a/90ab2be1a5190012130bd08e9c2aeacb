 package me.ThaH3lper.EpicBoss;
 
 import java.io.File;
 import java.io.IOException;
 import java.io.InputStream;
 import java.util.HashMap;
 import java.util.Iterator;
 import java.util.List;
 import java.util.Map.Entry;
 import java.util.Set;
 import java.util.logging.Level;
 import java.util.logging.Logger;
 import org.bukkit.Bukkit;
 import org.bukkit.ChatColor;
 import org.bukkit.Effect;
 import org.bukkit.Location;
 import org.bukkit.Server;
 import org.bukkit.World;
 import org.bukkit.command.Command;
 import org.bukkit.command.CommandSender;
 import org.bukkit.configuration.ConfigurationSection;
 import org.bukkit.configuration.file.FileConfiguration;
 import org.bukkit.configuration.file.FileConfigurationOptions;
 import org.bukkit.configuration.file.YamlConfiguration;
 import org.bukkit.entity.EntityType;
 import org.bukkit.entity.LivingEntity;
 import org.bukkit.entity.Player;
 import org.bukkit.entity.Slime;
 import org.bukkit.plugin.PluginDescriptionFile;
 import org.bukkit.plugin.PluginManager;
 import org.bukkit.plugin.java.JavaPlugin;
 import org.bukkit.scheduler.BukkitScheduler;
 
 public class EpicBoss extends JavaPlugin
 {
   public final Logger logger = Logger.getLogger("Minecraft");
   public String prefix = ChatColor.GRAY + "[" + ChatColor.DARK_RED + "EpicBoss" + ChatColor.GRAY + "] ";
   public static EpicBoss pl;
   public static API API;
   public static SpawnBoss Spawnboss;
   public static FileConfiguration DataConfig = null;
   public static File data = null;
 
   public static FileConfiguration DataConfigo = null;
   public static File datao = null;
 
   public HashMap<String, Integer> map = new HashMap();
   File filee = new File("plugins/EpicBoss/config.yml");
   File fileO = new File("plugins/EpicBoss/Options.yml");
 
   public boolean getboss = false;
   public boolean getTimer = false;
   public String getTimers;
   public LivingEntity lp;
   public String file = "Data.yml";
   public String fileo = "Options.yml";
   public String bName = null;
   public List<String> skills;
   public Location l;
 
   public EpicBoss()
   {
     API api = new API(this);
   }
 
   public void onDisable()
   {
     if (getCustomConfig(this.file).contains("mobs"))
     {
       for (String w : getCustomConfig(this.file).getConfigurationSection("mobs").getKeys(false))
       {
         for (World world : Bukkit.getServer().getWorlds())
         {
           for (LivingEntity e : world.getLivingEntities())
           {
             if (e.getEntityId() != Integer.parseInt(w))
               continue;
             getCustomConfig(this.file).set("savemobs." + e.getEntityId() + ".w", e.getWorld().getName());
             getCustomConfig(this.file).set("savemobs." + e.getEntityId() + ".x", Double.valueOf(e.getLocation().getX()));
             getCustomConfig(this.file).set("savemobs." + e.getEntityId() + ".y", Double.valueOf(e.getLocation().getY()));
             getCustomConfig(this.file).set("savemobs." + e.getEntityId() + ".z", Double.valueOf(e.getLocation().getZ()));
             getCustomConfig(this.file).set("savemobs." + e.getEntityId() + ".type", getCustomConfig(this.file).getString("mobs." + w + ".Name"));
             getCustomConfig(this.file).set("mobs." + w, null);
             saveCustomConfig(this.file);
             e.remove();
           }
         }
       }
 
     }
 
     if (getCustomConfig(this.file).contains("Timers"))
     {
       for (String w : getCustomConfig(this.file).getConfigurationSection("Timers").getKeys(false))
       {
         if (!getCustomConfig(this.file).get("Timers." + w + ".on").equals("on"))
           continue;
         String s = getCustomConfig(this.file).getString("Timers." + w + ".id");
         String location = getCustomConfig(this.file).getString("Timers." + w + ".location");
         getCustomConfig(this.file).set("savemobs." + s + ".w", getCustomConfig(this.file).getString("Location." + location + ".w"));
         getCustomConfig(this.file).set("savemobs." + s + ".x", Double.valueOf(getCustomConfig(this.file).getDouble("Location." + location + ".x")));
         getCustomConfig(this.file).set("savemobs." + s + ".y", Double.valueOf(getCustomConfig(this.file).getDouble("Location." + location + ".y")));
         getCustomConfig(this.file).set("savemobs." + s + ".z", Double.valueOf(getCustomConfig(this.file).getDouble("Location." + location + ".z")));
         getCustomConfig(this.file).set("savemobs." + s + ".type", getCustomConfig(this.file).getString("Timers." + w + ".type"));
         getCustomConfig(this.file).set("Timers." + w + ".on", "no");
         saveCustomConfig(this.file);
       }
     }
 
     PluginDescriptionFile pdfFile = getDescription();
     this.logger.info("[EpicBoss] " + pdfFile.getVersion() + " Has Been Disabled!");
     getCustomConfig(this.file).set("mobs", null);
     saveCustomConfig(this.file);
   }
 
   public void onEnable()
   {
     PluginDescriptionFile pdfFile = getDescription();
     this.logger.info("[EpicBoss] " + pdfFile.getVersion() + " Has Been Enabled!");
 
     PluginManager manager = getServer().getPluginManager();
     manager.registerEvents(new SpawnBoss(this), this);
     if (!this.filee.exists())
     {
       getConfig().options().copyDefaults(true);
       saveConfig();
     }
     getCustomConfig(this.file).options().copyDefaults(true);
     saveCustomConfig(this.file);
     if (!this.fileO.exists())
     {
       getOptionConfig(this.fileo).options().copyDefaults(true);
       saveResource(this.fileo, false);
     }
     loop();
     try
     {
       Metrics metrics = new Metrics(this);
       metrics.start();
     } catch (IOException e) {
       this.logger.info("[EpicBoss] Metrics Failed");
     }
 
     if (getHeroes())
     {
       this.logger.info("[EpicBoss] Hooked into [Heroes]! :D");
     }
   }
 
   public boolean onCommand(CommandSender sender, Command cmd, String commandlabel, String[] args)
   {
     if ((commandlabel.equalsIgnoreCase("eb")) || (commandlabel.equalsIgnoreCase("epicboss")))
     {
       if (Message.SendPlayer(sender))
       {
         Player player = (Player)sender;
         if (args.length == 0)
         {
           Error(player);
         }
         if (args.length == 1)
         {
           if (args[0].equals("killall"))
           {
             if (player.hasPermission("epicboss.killall"))
             {
               if (getCustomConfig(this.file).contains("savemobs"))
               {
                 for (String ssave : getCustomConfig(this.file).getConfigurationSection("savemobs").getKeys(false))
                 {
                   if (Timers(ssave))
                     continue;
                   getCustomConfig(this.file).set("savemobs." + ssave, null);
                   saveCustomConfig(this.file);
                 }
 
               }
 
               for (World world : Bukkit.getServer().getWorlds())
               {
                 for (LivingEntity e : world.getLivingEntities())
                 {
                   if (!getCustomConfig(this.file).contains("mobs"))
                     continue;
                   for (String smobs : getCustomConfig(this.file).getConfigurationSection("mobs").getKeys(false))
                   {
                     Integer i = Integer.valueOf(Integer.parseInt(smobs));
                     if (e.getEntityId() != i.intValue())
                       continue;
                     if (Timers(smobs))
                       continue;
                     getCustomConfig(this.file).set("mobs." + smobs, null);
                     saveCustomConfig(this.file);
                     e.setHealth(0);
                   }
 
                 }
 
               }
 
             }
             else
             {
               noPerm(player);
             }
           }
           else if (args[0].equals("reload"))
           {
             if (player.hasPermission("epicboss.reload"))
             {
               reloadConfig();
               saveConfig();
               reloadOptionConfig(this.fileo);
               saveResource(this.fileo, false);
               player.sendMessage(this.prefix + ChatColor.GREEN + "has been reloaded!");
             }
             else {
               noPerm(player);
             }
           } else if (args[0].equals("p2"))
           {
             p2(player);
           }
           else if (args[0].equals("timers"))
           {
             if (player.hasPermission("epicboss.timers"))
             {
               Message.sLoc = "";
               if (getCustomConfig(this.file).contains("Timers"))
               {
                 for (String s : getCustomConfig(this.file).getConfigurationSection("Timers").getKeys(false))
                 {
                   Message.sLoc = Message.sLoc + ChatColor.DARK_PURPLE + s + ChatColor.GRAY + ", ";
                 }
               }
               else
               {
                 Message.sLoc = ChatColor.DARK_RED + "There is no timers!";
               }
               player.sendMessage(this.prefix + ChatColor.GOLD + ChatColor.BOLD + "TIMERS");
               player.sendMessage(this.prefix + ChatColor.DARK_PURPLE + Message.sLoc);
             }
             else
             {
               noPerm(player);
             }
 
           }
           else
           {
             Error(player);
           }
         }
         Iterator iter;
         if (args.length == 2)
         {
           if (args[0].equals("killtype"))
           {
             if (player.hasPermission("epicboss.killtype"))
             {
               if (getConfig().contains("Bosses." + args[1]))
               {
                 if (getCustomConfig(this.file).contains("savemobs"))
                 {
                   for (String ssave : getCustomConfig(this.file).getConfigurationSection("savemobs").getKeys(false))
                   {
                     if (!getCustomConfig(this.file).get("savemobs." + ssave + ".type").equals(args[1])) {
                       continue;
                     }
                     if (Timers(ssave))
                       continue;
                     getCustomConfig(this.file).set("savemobs." + ssave, null);
                     saveCustomConfig(this.file);
                   }
 
                 }
 
                 for (World world : Bukkit.getServer().getWorlds())
                 {
                   for (LivingEntity e : world.getLivingEntities())
                   {
                     if (!getCustomConfig(this.file).contains("mobs"))
                       continue;
                     for (String smobs : getCustomConfig(this.file).getConfigurationSection("mobs").getKeys(false))
                     {
                       if (!getCustomConfig(this.file).get("mobs." + smobs + ".Name").equals(args[1]))
                         continue;
                       Integer i = Integer.valueOf(Integer.parseInt(smobs));
                       if (e.getEntityId() != i.intValue())
                         continue;
                       if (Timers(smobs))
                         continue;
                       getCustomConfig(this.file).set("mobs." + smobs, null);
                       saveCustomConfig(this.file);
                       e.setHealth(0);
                     }
 
                   }
 
                 }
 
               }
               else
               {
                 noBoss(player);
               }
 
             }
             else
             {
               noPerm(player);
             }
           }
           else if (args[0].equals("setlocation"))
           {
             if (player.hasPermission("epicboss.setlocation"))
             {
               String w = player.getWorld().getName();
               double x = player.getLocation().getX();
               double y = player.getLocation().getY();
               double z = player.getLocation().getZ();
               getCustomConfig(this.file).set("Location." + args[1] + ".w", w);
               getCustomConfig(this.file).set("Location." + args[1] + ".x", Double.valueOf(x));
               getCustomConfig(this.file).set("Location." + args[1] + ".y", Double.valueOf(y));
               getCustomConfig(this.file).set("Location." + args[1] + ".z", Double.valueOf(z));
               saveCustomConfig(this.file);
               player.sendMessage(this.prefix + ChatColor.GREEN + args[1] + " has been set!");
             }
             else
             {
               noPerm(player);
             }
           }
           else if (args[0].equals("removelocation"))
           {
             if (player.hasPermission("epicboss.removelocation"))
             {
               if (getCustomConfig(this.file).contains("Location." + args[1]))
               {
                 getCustomConfig(this.file).set("Location." + args[1], null);
                 saveCustomConfig(this.file);
                 player.sendMessage(this.prefix + ChatColor.GREEN + args[1] + " has been removed!");
               }
               else
               {
                 wrongloc(player);
               }
             }
             else
             {
               noPerm(player);
             }
           }
           else if (args[0].equals("timers"))
           {
             if (player.hasPermission("epicboss.timers"))
             {
               if (getCustomConfig(this.file).contains("Timers"))
               {
                 if (getCustomConfig(this.file).contains("Timers." + args[1]))
                 {
                   player.sendMessage(this.prefix + ChatColor.GRAY + "Name: " + ChatColor.DARK_PURPLE + args[1]);
                   player.sendMessage(this.prefix + ChatColor.GRAY + "Type: " + ChatColor.DARK_PURPLE + getCustomConfig(this.file).getString(new StringBuilder("Timers.").append(args[1]).append(".type").toString()));
                   player.sendMessage(this.prefix + ChatColor.GRAY + "Location: " + ChatColor.DARK_PURPLE + getCustomConfig(this.file).getString(new StringBuilder("Timers.").append(args[1]).append(".location").toString()));
 
                   int time = getCustomConfig(this.file).getInt("Timers." + args[1] + ".ticks");
                   player.sendMessage(this.prefix + ChatColor.GRAY + "RespawnTime: " + ChatColor.DARK_PURPLE + timerInfo(Integer.valueOf(time)));
 
                   iter = this.map.entrySet().iterator();
 
                   int time2 = 0;
                   while (iter.hasNext()) {
                     Map.Entry entry = (Map.Entry)iter.next();
                     if (!((String)entry.getKey()).equals(args[1]))
                       continue;
                     time2 = ((Integer)entry.getValue()).intValue();
                   }
 
                   player.sendMessage(this.prefix + ChatColor.GRAY + "RespawnIn: " + ChatColor.DARK_PURPLE + timerInfo(Integer.valueOf(time2)));
                 }
                 else
                 {
                   player.sendMessage(this.prefix + ChatColor.RED + "That timer dosen't exict!");
                 }
               }
               else
               {
                 player.sendMessage(this.prefix + ChatColor.RED + "There is no timers!");
               }
             }
           }
           else
           {
             Error(player);
           }
         }
         if (args.length == 3)
         {
           if (args[0].equals("spawn"))
           {
             if (player.hasPermission("epicboss.spawn"))
             {
               Boss(player, args[1], args[2]);
             }
             else
             {
               noPerm(player);
             }
           }
           else if ((args[0].equals("timer")) && (args[1].equals("remove")))
           {
             if (player.hasPermission("epicboss.removetimer"))
             {
               if (getCustomConfig(this.file).contains("Timers"))
               {
                 for (String s : getCustomConfig(this.file).getConfigurationSection("Timers").getKeys(false))
                 {
                   if (!args[2].equals(s))
                     continue;
                   getCustomConfig(this.file).set("Timers." + s, null);
                   player.sendMessage(this.prefix + ChatColor.GREEN + s + " has been removed!");
                 }
               }
 
             }
             else
             {
               noPerm(player);
             }
           }
           else
           {
             Error(player);
           }
         }
         if (args.length == 5)
         {
           if (args[0].equals("timer"))
           {
             if (player.hasPermission("epicboss.newtimer"))
             {
               String name = args[1];
               if (getConfig().contains("Bosses." + args[2]))
               {
                 if (getCustomConfig(this.file).contains("Location." + args[3]))
                 {
                   String[] time = args[4].split(":");
                   Integer h = parseInteger(time[0]);
                   Integer m = parseInteger(time[1]);
                   Integer s = parseInteger(time[2]);
                   if ((h != null) && (m != null) && (s != null))
                   {
                     Integer TimeTicks = Integer.valueOf(s.intValue() + m.intValue() * 60 + h.intValue() * 60 * 60);
                     getCustomConfig(this.file).set("Timers." + name + ".type", args[2]);
                     getCustomConfig(this.file).set("Timers." + name + ".location", args[3]);
                     getCustomConfig(this.file).set("Timers." + name + ".ticks", TimeTicks);
                     BossTimer(args[2], args[3]);
                     getCustomConfig(this.file).set("Timers." + name + ".id", Integer.valueOf(this.lp.getEntityId()));
                     getCustomConfig(this.file).set("Timers." + name + ".on", "no");
                     saveCustomConfig(this.file);
                     player.sendMessage(this.prefix + ChatColor.GREEN + ChatColor.DARK_GREEN + ChatColor.ITALIC + name + ChatColor.GREEN + " created at " + ChatColor.DARK_GREEN + ChatColor.ITALIC + args[3] + ChatColor.GREEN + " with boss " + ChatColor.DARK_GREEN + ChatColor.ITALIC + args[2] + ChatColor.GREEN + " and respawns after " + ChatColor.DARK_GREEN + ChatColor.ITALIC + h + "hours, " + m + "mins, " + s + "seconds");
                   }
                   else {
                     player.sendMessage(this.prefix + ChatColor.RED + "Timer respawntime error, you made it wrong :/");
                   }
                 } else {
                   player.sendMessage(this.prefix + ChatColor.RED + "There is no location with that name!");
                 }
               }
               else player.sendMessage(this.prefix + ChatColor.RED + "There is no boss with that name!");
             }
           }
 
         }
 
       }
       else if (args.length == 3)
       {
         if (args[0].equals("spawn"))
         {
           Boss(null, args[1], args[2]);
         }
       }
       else
       {
         sender.sendMessage("/eb spawn <boss> <location>");
       }
     }
 
     return false;
   }
 
   public String timerInfo(Integer i)
   {
     int m = 0;
     int h = 0;
     int s = 0;
     if (i.intValue() >= 60)
     {
       s = i.intValue() % 60;
       i = Integer.valueOf(i.intValue() - s);
       i = Integer.valueOf(i.intValue() / 60);
     }
     else
     {
       s = i.intValue();
       return h + "hour(s), " + m + "minut(s), " + s + "second(s)";
     }
     if (i.intValue() >= 60)
     {
       m = i.intValue() % 60;
       i = Integer.valueOf(i.intValue() - m);
       h = i.intValue() / 60;
     }
     else
     {
       m = i.intValue();
       return h + "hour(s), " + m + "minut(s), " + s + "second(s)";
     }
     return h + "hour(s), " + m + "minut(s), " + s + "second(s)";
   }
 
   public boolean Timers(String s) {
     if (getCustomConfig(this.file).contains("Timers"))
     {
       for (String stimer : getCustomConfig(this.file).getConfigurationSection("Timers").getKeys(false))
       {
         if (getCustomConfig(this.file).getString("Timers." + stimer + ".id").equals(s))
         {
           return true;
         }
       }
     }
 
     return false;
   }
 
   public void Error(Player p)
   {
     Message.sbosses = "";
     Message.sLoc = "";
     for (String s : getConfig().getConfigurationSection("Bosses").getKeys(false))
     {
       Message.sbosses = Message.sbosses + ChatColor.DARK_PURPLE + s + ChatColor.GRAY + ", ";
     }
     if (getCustomConfig(this.file).contains("Location"))
     {
       for (String w : getCustomConfig(this.file).getConfigurationSection("Location").getKeys(false))
       {
         Message.sLoc = Message.sLoc + ChatColor.DARK_PURPLE + w + ChatColor.GRAY + ", ";
       }
     }
     if (Message.sLoc == "")
     {
       Message.sLoc = ChatColor.RED + "There is no Locations!";
     }
     p.sendMessage(this.prefix + ChatColor.GOLD + ChatColor.BOLD + "COMMANDS" + ChatColor.GRAY + ChatColor.ITALIC + "   /eb p2" + ChatColor.GRAY + " for next page");
     p.sendMessage(this.prefix + ChatColor.DARK_PURPLE + "/eb spawn <bossname> <Location/here>");
     p.sendMessage(this.prefix + ChatColor.DARK_PURPLE + "/eb setlocation <name>");
     p.sendMessage(this.prefix + ChatColor.DARK_PURPLE + "/eb removelocation <name>");
     p.sendMessage(this.prefix + ChatColor.DARK_PURPLE + "/eb killall");
     p.sendMessage(this.prefix + ChatColor.DARK_PURPLE + "/eb killtype <bossname>");
     p.sendMessage(this.prefix + ChatColor.GOLD + ChatColor.BOLD + "BOSSES");
     p.sendMessage(this.prefix + ChatColor.DARK_PURPLE + Message.sbosses);
     p.sendMessage(this.prefix + ChatColor.GOLD + ChatColor.BOLD + "LOCATIONS");
     p.sendMessage(this.prefix + ChatColor.DARK_PURPLE + Message.sLoc);
   }
 
   public void p2(Player p) {
     p.sendMessage(this.prefix + ChatColor.GOLD + ChatColor.BOLD + "MORE COMMANDS");
     p.sendMessage(this.prefix + ChatColor.DARK_PURPLE + "/eb timers");
     p.sendMessage(this.prefix + ChatColor.DARK_PURPLE + "/eb timer remove <name>");
     p.sendMessage(this.prefix + ChatColor.DARK_PURPLE + "/eb timer <name> <boss> <location> <h:min:sec>");
     p.sendMessage(this.prefix + ChatColor.DARK_PURPLE + "/eb reload");
   }
 
   public void noPerm(Player p) {
     p.sendMessage(this.prefix + ChatColor.RED + "You don't have permission to do that!");
   }
 
   public void noBoss(Player p) {
     p.sendMessage(this.prefix + ChatColor.RED + "That boss dosen't exict!");
   }
 
   public void wrongloc(Player p) {
     p.sendMessage(this.prefix + ChatColor.RED + "That Location dose not exict!");
   }
 
   public void Boss(Player p, String s, String d)
   {
     if (getConfig().contains("Bosses." + s))
     {
       if (getCustomConfig(this.file).contains("Location." + d))
       {
         Integer x = Integer.valueOf(getCustomConfig(this.file).getInt("Location." + d + ".x"));
         Integer y = Integer.valueOf(getCustomConfig(this.file).getInt("Location." + d + ".y"));
         Integer z = Integer.valueOf(getCustomConfig(this.file).getInt("Location." + d + ".z"));
         World w = Bukkit.getWorld(getCustomConfig(this.file).getString("Location." + d + ".w"));
         this.l = new Location(w, x.intValue(), y.intValue(), z.intValue());
       }
       else if (d.equals("here"))
       {
         if (p != null)
         {
           this.l = p.getLocation();
         }
       }
       else
       {
         this.l = null;
         if (p != null)
         {
           wrongloc(p);
         }
       }
       if (this.l != null)
       {
         int id = (int)(Math.random() * 99999.0D) + 100000;
 
         getCustomConfig(this.file).set("savemobs." + id + ".w", this.l.getWorld().getName());
         getCustomConfig(this.file).set("savemobs." + id + ".x", Double.valueOf(this.l.getX()));
         getCustomConfig(this.file).set("savemobs." + id + ".y", Double.valueOf(this.l.getY()));
         getCustomConfig(this.file).set("savemobs." + id + ".z", Double.valueOf(this.l.getZ()));
         getCustomConfig(this.file).set("savemobs." + id + ".type", s);
         if (p != null)
         {
           p.sendMessage(this.prefix + ChatColor.GREEN + "You spawned " + ChatColor.DARK_GREEN + ChatColor.ITALIC + s);
         }
         BossDespawn();
       }
 
     }
     else if (p != null)
     {
       noBoss(p);
     }
   }
 
   public void BossTimer(String s, String d)
   {
     if (getConfig().contains("Bosses." + s))
     {
       if (getCustomConfig(this.file).contains("Location." + d))
       {
         Integer x = Integer.valueOf(getCustomConfig(this.file).getInt("Location." + d + ".x"));
         Integer y = Integer.valueOf(getCustomConfig(this.file).getInt("Location." + d + ".y"));
         Integer z = Integer.valueOf(getCustomConfig(this.file).getInt("Location." + d + ".z"));
         World w = Bukkit.getWorld(getCustomConfig(this.file).getString("Location." + d + ".w"));
         this.l = new Location(w, x.intValue(), y.intValue(), z.intValue());
       }
 
       if (this.l != null)
       {
         this.getboss = true;
         this.bName = s;
         if (getConfig().get("Bosses." + s + ".Type").equals("mushroomcow"))
           this.l.getWorld().spawnEntity(this.l, EntityType.MUSHROOM_COW);
         if (getConfig().get("Bosses." + s + ".Type").equals("squid"))
           this.l.getWorld().spawnEntity(this.l, EntityType.SQUID);
         if (getConfig().get("Bosses." + s + ".Type").equals("skeleton"))
           this.l.getWorld().spawnEntity(this.l, EntityType.SKELETON);
         if (getConfig().get("Bosses." + s + ".Type").equals("ghast"))
           this.l.getWorld().spawnEntity(this.l, EntityType.GHAST);
         if (getConfig().get("Bosses." + s + ".Type").equals("blaze"))
           this.l.getWorld().spawnEntity(this.l, EntityType.BLAZE);
         if (getConfig().get("Bosses." + s + ".Type").equals("zombie"))
           this.l.getWorld().spawnEntity(this.l, EntityType.ZOMBIE);
         if (getConfig().get("Bosses." + s + ".Type").equals("slime"))
         {
           this.l.getWorld().spawnEntity(this.l, EntityType.SLIME);
           Slime slime = (Slime)this.lp;
           slime.setSize(5);
         }
         if (getConfig().get("Bosses." + s + ".Type").equals("wolf"))
           this.l.getWorld().spawnEntity(this.l, EntityType.WOLF);
         if (getConfig().get("Bosses." + s + ".Type").equals("irongolem"))
           this.l.getWorld().spawnEntity(this.l, EntityType.IRON_GOLEM);
         if (getConfig().get("Bosses." + s + ".Type").equals("pig"))
           this.l.getWorld().spawnEntity(this.l, EntityType.PIG);
         if (getConfig().get("Bosses." + s + ".Type").equals("sheep"))
           this.l.getWorld().spawnEntity(this.l, EntityType.SHEEP);
         if (getConfig().get("Bosses." + s + ".Type").equals("villager"))
           this.l.getWorld().spawnEntity(this.l, EntityType.VILLAGER);
         if (getConfig().get("Bosses." + s + ".Type").equals("ocelot"))
           this.l.getWorld().spawnEntity(this.l, EntityType.OCELOT);
         if (getConfig().get("Bosses." + s + ".Type").equals("chicken"))
           this.l.getWorld().spawnEntity(this.l, EntityType.CHICKEN);
         if (getConfig().get("Bosses." + s + ".Type").equals("cow"))
           this.l.getWorld().spawnEntity(this.l, EntityType.COW);
         if (getConfig().get("Bosses." + s + ".Type").equals("spider"))
           this.l.getWorld().spawnEntity(this.l, EntityType.SPIDER);
         if (getConfig().get("Bosses." + s + ".Type").equals("enderman"))
           this.l.getWorld().spawnEntity(this.l, EntityType.ENDERMAN);
         if (getConfig().get("Bosses." + s + ".Type").equals("cavespider"))
           this.l.getWorld().spawnEntity(this.l, EntityType.CAVE_SPIDER);
         if (getConfig().get("Bosses." + s + ".Type").equals("giant"))
           this.l.getWorld().spawnEntity(this.l, EntityType.GIANT);
         if (getConfig().get("Bosses." + s + ".Type").equals("silverfish"))
           this.l.getWorld().spawnEntity(this.l, EntityType.SILVERFISH);
         if (getConfig().get("Bosses." + s + ".Type").equals("magmacube"))
           this.l.getWorld().spawnEntity(this.l, EntityType.MAGMA_CUBE);
         if (getConfig().get("Bosses." + s + ".Type").equals("pigzombie"))
           this.l.getWorld().spawnEntity(this.l, EntityType.PIG_ZOMBIE);
         if (getConfig().get("Bosses." + s + ".Type").equals("enderdragon"))
             this.l.getWorld().spawnEntity(this.l, EntityType.ENDER_DRAGON);
       }
     }
   }
 
   public void BossQuit(Player p)
   {
     if (getCustomConfig(this.file).contains("mobs"))
     {
       for (String w : getCustomConfig(this.file).getConfigurationSection("mobs").getKeys(false))
       {
         for (World world : Bukkit.getServer().getWorlds())
         {
           for (LivingEntity e : world.getLivingEntities())
           {
             if (e.getEntityId() != Integer.parseInt(w))
               continue;
             if (!insidequit(e, p))
               continue;
             getCustomConfig(this.file).set("savemobs." + e.getEntityId() + ".w", e.getWorld().getName());
             getCustomConfig(this.file).set("savemobs." + e.getEntityId() + ".x", Double.valueOf(e.getLocation().getX()));
             getCustomConfig(this.file).set("savemobs." + e.getEntityId() + ".y", Double.valueOf(e.getLocation().getY()));
             getCustomConfig(this.file).set("savemobs." + e.getEntityId() + ".z", Double.valueOf(e.getLocation().getZ()));
             getCustomConfig(this.file).set("savemobs." + e.getEntityId() + ".type", getCustomConfig(this.file).getString("mobs." + w + ".Name"));
             getCustomConfig(this.file).set("mobs." + w, null);
             saveCustomConfig(this.file);
             e.remove();
           }
         }
       }
     }
   }
 
   public void BossDespawn()
   {
     Object localObject;
     if (getCustomConfig(this.file).contains("mobs"))
     {
       for (String w : getCustomConfig(this.file).getConfigurationSection("mobs").getKeys(false))
       {
         for (localObject = Bukkit.getServer().getWorlds().iterator(); ((Iterator)localObject).hasNext(); ) { world = (World)((Iterator)localObject).next();
 
           for (LivingEntity e : world.getLivingEntities())
           {
             if (e.getEntityId() != Integer.parseInt(w))
               continue;
             if (!insideradius(e))
               continue;
             getCustomConfig(this.file).set("savemobs." + e.getEntityId() + ".w", e.getWorld().getName());
             getCustomConfig(this.file).set("savemobs." + e.getEntityId() + ".x", Double.valueOf(e.getLocation().getX()));
             getCustomConfig(this.file).set("savemobs." + e.getEntityId() + ".y", Double.valueOf(e.getLocation().getY()));
             getCustomConfig(this.file).set("savemobs." + e.getEntityId() + ".z", Double.valueOf(e.getLocation().getZ()));
             getCustomConfig(this.file).set("savemobs." + e.getEntityId() + ".type", getCustomConfig(this.file).getString("mobs." + w + ".Name"));
             getCustomConfig(this.file).set("mobs." + w, null);
             saveCustomConfig(this.file);
             e.remove();
           }
         }
       }
 
     }
 
     World world = (localObject = Bukkit.getServer().getOnlinePlayers()).length; for (World localWorld1 = 0; localWorld1 < world; localWorld1++) { Player p = localObject[localWorld1];
 
       if (!getCustomConfig(this.file).contains("savemobs"))
         continue;
       for (String w : getCustomConfig(this.file).getConfigurationSection("savemobs").getKeys(false))
       {
         String t = getCustomConfig(this.file).getString("savemobs." + w + ".w");
         String s = getCustomConfig(this.file).getString("savemobs." + w + ".type");
         double x = getCustomConfig(this.file).getDouble("savemobs." + w + ".x");
         double y = getCustomConfig(this.file).getDouble("savemobs." + w + ".y");
         double z = getCustomConfig(this.file).getDouble("savemobs." + w + ".z");
         Location q = new Location(Bukkit.getServer().getWorld(t), x, y, z);
         if ((!p.getWorld().equals(q.getWorld())) || (p.isDead()))
           continue;
         if (p.getLocation().distance(q) > 30.0D)
           continue;
         if (!getConfig().contains("Bosses." + s))
           continue;
         this.l = q;
         if (this.l != null)
         {
           this.getboss = true;
           this.bName = s;
 
           if (getCustomConfig(this.file).contains("Timers"))
           {
             for (String k : getCustomConfig(this.file).getConfigurationSection("Timers").getKeys(false))
             {
               if (!getCustomConfig(this.file).getString("Timers." + k + ".id").equals(w.toString()))
                 continue;
               this.getTimers = w.toString();
               this.getTimer = true;
             }
           }
 
           if (getConfig().get("Bosses." + s + ".Type").equals("mushroomcow"))
             this.l.getWorld().spawnEntity(this.l, EntityType.MUSHROOM_COW);
           if (getConfig().get("Bosses." + s + ".Type").equals("squid"))
             this.l.getWorld().spawnEntity(this.l, EntityType.SQUID);
           if (getConfig().get("Bosses." + s + ".Type").equals("skeleton"))
             this.l.getWorld().spawnEntity(this.l, EntityType.SKELETON);
           if (getConfig().get("Bosses." + s + ".Type").equals("ghast"))
             this.l.getWorld().spawnEntity(this.l, EntityType.GHAST);
           if (getConfig().get("Bosses." + s + ".Type").equals("blaze"))
             this.l.getWorld().spawnEntity(this.l, EntityType.BLAZE);
           if (getConfig().get("Bosses." + s + ".Type").equals("zombie"))
             this.l.getWorld().spawnEntity(this.l, EntityType.ZOMBIE);
           if (getConfig().get("Bosses." + s + ".Type").equals("slime"))
           {
             this.l.getWorld().spawnEntity(this.l, EntityType.SLIME);
             Slime slime = (Slime)this.lp;
             slime.setSize(5);
           }
           if (getConfig().get("Bosses." + s + ".Type").equals("wolf"))
             this.l.getWorld().spawnEntity(this.l, EntityType.WOLF);
           if (getConfig().get("Bosses." + s + ".Type").equals("irongolem"))
             this.l.getWorld().spawnEntity(this.l, EntityType.IRON_GOLEM);
           if (getConfig().get("Bosses." + s + ".Type").equals("pig"))
             this.l.getWorld().spawnEntity(this.l, EntityType.PIG);
           if (getConfig().get("Bosses." + s + ".Type").equals("sheep"))
             this.l.getWorld().spawnEntity(this.l, EntityType.SHEEP);
           if (getConfig().get("Bosses." + s + ".Type").equals("villager"))
             this.l.getWorld().spawnEntity(this.l, EntityType.VILLAGER);
           if (getConfig().get("Bosses." + s + ".Type").equals("ocelot"))
             this.l.getWorld().spawnEntity(this.l, EntityType.OCELOT);
           if (getConfig().get("Bosses." + s + ".Type").equals("chicken"))
             this.l.getWorld().spawnEntity(this.l, EntityType.CHICKEN);
           if (getConfig().get("Bosses." + s + ".Type").equals("cow"))
             this.l.getWorld().spawnEntity(this.l, EntityType.COW);
           if (getConfig().get("Bosses." + s + ".Type").equals("spider"))
             this.l.getWorld().spawnEntity(this.l, EntityType.SPIDER);
           if (getConfig().get("Bosses." + s + ".Type").equals("enderman"))
             this.l.getWorld().spawnEntity(this.l, EntityType.ENDERMAN);
           if (getConfig().get("Bosses." + s + ".Type").equals("cavespider"))
             this.l.getWorld().spawnEntity(this.l, EntityType.CAVE_SPIDER);
           if (getConfig().get("Bosses." + s + ".Type").equals("giant"))
             this.l.getWorld().spawnEntity(this.l, EntityType.GIANT);
           if (getConfig().get("Bosses." + s + ".Type").equals("silverfish"))
             this.l.getWorld().spawnEntity(this.l, EntityType.SILVERFISH);
           if (getConfig().get("Bosses." + s + ".Type").equals("magmacube"))
             this.l.getWorld().spawnEntity(this.l, EntityType.MAGMA_CUBE);
           if (getConfig().get("Bosses." + s + ".Type").equals("pigzombie")) {
             this.l.getWorld().spawnEntity(this.l, EntityType.PIG_ZOMBIE);
           }
         }
         getCustomConfig(this.file).set("savemobs." + w, null);
       }
     }
   }
 
   public boolean insideradius(LivingEntity e)
   {
     for (Player p : Bukkit.getServer().getOnlinePlayers())
     {
       if (p.isDead())
         continue;
       if (!p.getWorld().equals(e.getWorld()))
         continue;
       if (p.getLocation().distance(e.getLocation()) <= 40.0D)
       {
         return false;
       }
 
     }
 
     return true;
   }
 
   public boolean insidequit(LivingEntity e, Player ps) {
     for (Player p : Bukkit.getServer().getOnlinePlayers())
     {
       if (p.isDead())
         continue;
       if (!p.getWorld().equals(e.getWorld()))
         continue;
       if (p.getLocation().distance(e.getLocation()) > 40.0D)
         continue;
       if (p != ps)
       {
         return false;
       }
 
     }
 
     return true;
   }
 
   public void SpawnAfterZero(String string)
   {
     String type = getCustomConfig(this.file).getString("Timers." + string + ".type");
     String location = getCustomConfig(this.file).getString("Timers." + string + ".location");
     String ID = getCustomConfig(this.file).getString("Timers." + string + ".id");
     getCustomConfig(this.file).set("Timers." + string + ".on", "no");
     getCustomConfig(this.file).set("savemobs." + ID + ".w", getCustomConfig(this.file).getString("Location." + location + ".w"));
     getCustomConfig(this.file).set("savemobs." + ID + ".x", Double.valueOf(getCustomConfig(this.file).getDouble("Location." + location + ".x")));
     getCustomConfig(this.file).set("savemobs." + ID + ".y", Double.valueOf(getCustomConfig(this.file).getDouble("Location." + location + ".y")));
     getCustomConfig(this.file).set("savemobs." + ID + ".z", Double.valueOf(getCustomConfig(this.file).getDouble("Location." + location + ".z")));
     getCustomConfig(this.file).set("savemobs." + ID + ".type", type);
     saveCustomConfig(this.file);
   }
 
   public void saveall()
   {
     if (getCustomConfig(this.file).contains("mobs"))
     {
       for (String w : getCustomConfig(this.file).getConfigurationSection("mobs").getKeys(false))
       {
         for (World world : Bukkit.getServer().getWorlds())
         {
           for (LivingEntity e : world.getLivingEntities())
           {
             if (e.getEntityId() != Integer.parseInt(w))
               continue;
             getCustomConfig(this.file).set("savemobs." + e.getEntityId() + ".w", e.getWorld().getName());
             getCustomConfig(this.file).set("savemobs." + e.getEntityId() + ".x", Double.valueOf(e.getLocation().getX()));
             getCustomConfig(this.file).set("savemobs." + e.getEntityId() + ".y", Double.valueOf(e.getLocation().getY()));
             getCustomConfig(this.file).set("savemobs." + e.getEntityId() + ".z", Double.valueOf(e.getLocation().getZ()));
             getCustomConfig(this.file).set("savemobs." + e.getEntityId() + ".type", getCustomConfig(this.file).getString("mobs." + w + ".Name"));
             getCustomConfig(this.file).set("mobs." + w, null);
             saveCustomConfig(this.file);
             e.remove();
           }
         }
       }
     }
   }
 
   public void reloadCustomConfig(String sfile)
   {
     if (data == null) {
       data = new File(getDataFolder(), sfile);
     }
     DataConfig = YamlConfiguration.loadConfiguration(data);
 
     InputStream defConfigStream = getResource(sfile);
     if (defConfigStream != null) {
       YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
       DataConfig.setDefaults(defConfig);
     }
   }
 
   public FileConfiguration getCustomConfig(String sfile) {
     if (DataConfig == null) {
       reloadCustomConfig(sfile);
     }
     return DataConfig;
   }
 
   public void saveCustomConfig(String sfile) {
     if ((DataConfig == null) || (data == null))
       return;
     try
     {
       getCustomConfig(sfile).save(data);
     } catch (IOException ex) {
       getLogger().log(Level.SEVERE, "Could not save config to " + data, ex);
     }
   }
 
   public void reloadOptionConfig(String sfile)
   {
     if (datao == null) {
       datao = new File(getDataFolder(), sfile);
     }
     DataConfigo = YamlConfiguration.loadConfiguration(datao);
 
     InputStream defConfigStream = getResource(sfile);
     if (defConfigStream != null) {
       YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
       DataConfigo.setDefaults(defConfig);
     }
   }
 
   public FileConfiguration getOptionConfig(String sfile) {
     if (DataConfigo == null) {
       reloadOptionConfig(sfile);
     }
     return DataConfigo;
   }
 
   public void saveOptionConfig(String sfile) {
     if ((DataConfigo == null) || (datao == null))
       return;
     try
     {
       getOptionConfig(sfile).save(datao);
     } catch (IOException ex) {
       getLogger().log(Level.SEVERE, "Could not save config to " + datao, ex);
     }
   }
 
   public void Skills(LivingEntity l)
   {
     String s = getCustomConfig(this.file).getString("mobs." + l.getEntityId() + ".Name");
     this.skills = getConfig().getStringList("Bosses." + s + ".Skills");
   }
   private static Integer parseInteger(String s) {
     try {
       return Integer.valueOf(Integer.parseInt(s.trim()));
     } catch (Exception e) {
     }
     return null;
   }
 
   public void effectBoss()
   {
     for (World world : Bukkit.getServer().getWorlds())
     {
       for (LivingEntity e : world.getLivingEntities())
       {
         if (!getCustomConfig(this.file).contains("mobs." + e.getEntityId()))
           continue;
         Skills(e);
         if (!this.skills.contains("effectfire"))
           continue;
         e.getWorld().playEffect(e.getLocation(), Effect.MOBSPAWNER_FLAMES, 4);
       }
     }
   }
 
   public void loop()
   {
     Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable()
     {
       public void run() {
         EpicBoss.this.BossDespawn();
         EpicBoss.this.effectBoss();
         EpicBoss.this.FixTimerCrash();
         if (EpicBoss.this.map != null)
         {
           Iterator iter = EpicBoss.this.map.entrySet().iterator();
 
           while (iter.hasNext()) {
             Map.Entry entry = (Map.Entry)iter.next();
             if (((Integer)entry.getValue()).intValue() == 0) {
               EpicBoss.this.SpawnAfterZero((String)entry.getKey());
               iter.remove();
             }
             else {
               entry.setValue(Integer.valueOf(((Integer)entry.getValue()).intValue() - 1));
             }
           }
         }
       }
     }
     , 0L, 20L);
   }
 
   public boolean getHeroes()
   {
     return Bukkit.getServer().getPluginManager().getPlugin("Heroes") != null;
   }
 
   public boolean BossExict(LivingEntity l)
   {
     int id = l.getEntityId();
     for (World world : Bukkit.getServer().getWorlds())
     {
       for (LivingEntity e : world.getLivingEntities())
       {
         if (e.getEntityId() == id)
         {
           return true;
         }
       }
     }
     if (getCustomConfig(this.file).contains("savemobs"))
     {
       for (String w : getCustomConfig(this.file).getConfigurationSection("savemobs").getKeys(false))
       {
         if (Integer.parseInt(w) == id)
         {
           return true;
         }
       }
     }
     if (getCustomConfig(this.file).contains("mobs"))
     {
       for (String m : getCustomConfig(this.file).getConfigurationSection("mobs").getKeys(false))
       {
         if (Integer.parseInt(m) == id)
         {
           return true;
         }
       }
     }
     return false;
   }
 
   public void FixTimerCrash() {
     if (getCustomConfig(this.file).contains("Timers"))
     {
       for (String w : getCustomConfig(this.file).getConfigurationSection("Timers").getKeys(false))
       {
         if (!getCustomConfig(this.file).get("Timers." + w + ".on").equals("no"))
           continue;
         int id = getCustomConfig(this.file).getInt("Timers." + w + ".id");
         if (CheakEntity(id))
           continue;
         SpawnAfterZero(w);
       }
     }
   }
 
   public boolean CheakEntity(int id)
   {
     for (World world : Bukkit.getServer().getWorlds())
     {
       for (LivingEntity e : world.getLivingEntities())
       {
         if (e.getEntityId() == id)
         {
           return true;
         }
       }
     }
     return false;
   }
 }
