 package me.ThaH3lper.EpicBoss;
 
 import com.herocraftonline.heroes.Heroes;
 import com.herocraftonline.heroes.characters.CharacterDamageManager;
 import com.herocraftonline.heroes.characters.CharacterManager;
 import com.herocraftonline.heroes.characters.Monster;
 import java.util.ArrayList;
 import java.util.HashMap;
 import java.util.Iterator;
 import java.util.List;
 import java.util.Random;
 import org.bukkit.Bukkit;
 import org.bukkit.ChatColor;
 import org.bukkit.Location;
 import org.bukkit.Material;
 import org.bukkit.Server;
 import org.bukkit.World;
 import org.bukkit.configuration.ConfigurationSection;
 import org.bukkit.configuration.file.FileConfiguration;
 import org.bukkit.enchantments.Enchantment;
 import org.bukkit.entity.Arrow;
 import org.bukkit.entity.Entity;
 import org.bukkit.entity.EntityType;
 import org.bukkit.entity.Fireball;
 import org.bukkit.entity.HumanEntity;
 import org.bukkit.entity.LivingEntity;
 import org.bukkit.entity.Player;
 import org.bukkit.entity.SmallFireball;
 import org.bukkit.entity.Wolf;
 import org.bukkit.event.EventHandler;
 import org.bukkit.event.EventPriority;
 import org.bukkit.event.Listener;
 import org.bukkit.event.entity.CreatureSpawnEvent;
 import org.bukkit.event.entity.EntityDamageByEntityEvent;
 import org.bukkit.event.entity.EntityDamageEvent;
 import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
 import org.bukkit.event.entity.EntityTameEvent;
 import org.bukkit.event.player.PlayerQuitEvent;
 import org.bukkit.inventory.ItemStack;
 import org.bukkit.inventory.PlayerInventory;
 import org.bukkit.plugin.PluginManager;
 import org.bukkit.potion.PotionEffect;
 import org.bukkit.potion.PotionEffectType;
 import org.bukkit.util.Vector;
 
 public class SpawnBoss
   implements Listener
 {
   private EpicBoss plugin;
   public String[] items;
   public List<String> ItemList;
   public String rawString;
   public Player dmger;
   public ArrayList<String> prot = new ArrayList();
   public ArrayList<Integer> lvl = new ArrayList();
 
   public SpawnBoss(EpicBoss plugin)
   {
     this.plugin = plugin;
   }
 
   @EventHandler(priority=EventPriority.HIGH)
   public void getBoss(CreatureSpawnEvent event) {
     if (this.plugin.getboss)
     {
       this.plugin.getboss = false;
       this.plugin.lp = event.getEntity();
       this.plugin.lp.setHealth(this.plugin.lp.getMaxHealth() - 1);
       if (this.plugin.getTimer)
       {
         for (String k : this.plugin.getCustomConfig(this.plugin.file).getConfigurationSection("Timers").getKeys(false))
         {
           if (!this.plugin.getCustomConfig(this.plugin.file).getString("Timers." + k + ".id").equals(this.plugin.getTimers))
             continue;
           this.plugin.getCustomConfig(this.plugin.file).set("Timers." + k + ".id", Integer.valueOf(this.plugin.lp.getEntityId()));
           this.plugin.getTimer = false;
         }
       }
 
       String s = "mobs." + this.plugin.lp.getEntityId();
 
       this.plugin.getCustomConfig(this.plugin.file).set(s + ".Health", this.plugin.getConfig().get("Bosses." + this.plugin.bName + ".Health"));
       this.plugin.getCustomConfig(this.plugin.file).set(s + ".Name", this.plugin.bName);
       this.plugin.saveCustomConfig(this.plugin.file);
       String sk;
       if (this.plugin.getHeroes())
       {
         Heroes heroes = (Heroes)Bukkit.getPluginManager().getPlugin("Heroes");
 
         sk = this.plugin.getCustomConfig(this.plugin.file).getString("mobs." + this.plugin.lp.getEntityId() + ".Name");
         Integer dmg = Integer.valueOf(this.plugin.getConfig().getInt("Bosses." + sk + ".Damage"));
 
         heroes.getCharacterManager().getMonster(this.plugin.lp).setDamage(dmg.intValue());
         heroes.getCharacterManager().getMonster(this.plugin.lp).setMaxHealth(this.plugin.getConfig().getInt("Bosses." + sk + ".Health"));
       }
 
       this.plugin.Skills(this.plugin.lp);
       for (String str : this.plugin.skills)
       {
         if (str.equals("speed_1"))
           this.plugin.lp.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 10000, 1));
         if (str.equals("speed_2"))
           this.plugin.lp.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 10000, 2));
         if (str.equals("jump"))
           this.plugin.lp.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 10000, 2));
         if (str.equals("slow"))
           this.plugin.lp.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 10000, 2)); 
       }
     }
   }
 
   @EventHandler(priority=EventPriority.HIGH)
   public void Heath(EntityDamageByEntityEvent event) {
     Entity damager = event.getDamager();
     Entity hitEntity = event.getEntity();
     if ((damager instanceof Arrow))
     {
       Arrow a = (Arrow)event.getDamager();
       damager = a.getShooter();
       if (damager == null)
       {
         event.setCancelled(true);
         return;
       }
     }
     if ((damager instanceof Fireball))
     {
       Fireball a = (Fireball)event.getDamager();
       damager = a.getShooter();
     }
     if ((damager instanceof SmallFireball))
     {
       SmallFireball a = (SmallFireball)event.getDamager();
       damager = a.getShooter();
     }
     if ((this.plugin.getCustomConfig(this.plugin.file).contains("mobs." + damager.getEntityId())) && ((hitEntity instanceof HumanEntity)))
     {
       String s = this.plugin.getCustomConfig(this.plugin.file).getString("mobs." + damager.getEntityId() + ".Name");
       Integer dmg = Integer.valueOf(this.plugin.getConfig().getInt("Bosses." + s + ".Damage"));
       event.setDamage(dmg.intValue());
     }
     if (this.plugin.getCustomConfig(this.plugin.file).contains("mobs." + hitEntity.getEntityId()))
     {
       int hitamount = event.getDamage();
       if (((damager instanceof HumanEntity)) || ((damager instanceof Wolf)))
       {
         if ((((damager instanceof HumanEntity)) && ((hitEntity instanceof LivingEntity))) || ((damager instanceof Wolf)))
         {
           if ((damager instanceof HumanEntity))
           {
             this.dmger = ((Player)damager);
           }
           this.plugin.lp = ((LivingEntity)hitEntity);
 
           if (!this.plugin.getHeroes())
           {
             event.setDamage(2);
           }
           if (this.plugin.lp.getHealth() != this.plugin.lp.getMaxHealth())
           {
             int health = this.plugin.getCustomConfig(this.plugin.file).getInt("mobs." + hitEntity.getEntityId() + ".Health");
             if (this.plugin.getHeroes())
             {
               Heroes heroes = (Heroes)Bukkit.getPluginManager().getPlugin("Heroes");
               health = heroes.getCharacterManager().getMonster(this.plugin.lp).getHealth();
               if ((damager instanceof HumanEntity))
               {
                 HumanEntity p = (HumanEntity)damager;
                 int hit = 0;
                 if (heroes.getDamageManager().getItemDamage(p.getInventory().getItemInHand().getType(), p) != null)
                 {
                   hit = heroes.getDamageManager().getItemDamage(p.getInventory().getItemInHand().getType(), p).intValue();
                 }
                 else
                 {
                   hit = event.getDamage();
                 }
                 health -= hit;
               }
               else if ((damager instanceof Wolf))
               {
                 int hit2 = heroes.getCharacterManager().getMonster((LivingEntity)damager).getDamage();
                 health -= hit2;
               }
 
             }
             else
             {
               health -= hitamount;
             }
             this.plugin.getCustomConfig(this.plugin.file).set("mobs." + hitEntity.getEntityId() + ".Health", Integer.valueOf(health));
             this.plugin.saveCustomConfig(this.plugin.file);
             Integer hp = Integer.valueOf(this.plugin.lp.getMaxHealth());
             this.plugin.lp.setHealth(hp.intValue());
             String pref;
             if ((health > 0) && ((damager instanceof HumanEntity)))
             {
               if (!this.plugin.getOptionConfig(this.plugin.fileo).getString("ShowHealth").equals("none"))
               {
                 if (this.plugin.getOptionConfig(this.plugin.fileo).getString("ShowHealth").equals("percent"))
                 {
                   String s = this.plugin.getCustomConfig(this.plugin.file).getString("mobs." + hitEntity.getEntityId() + ".Name");
                   String k = s.replace("_", " ");
 
                   String pref = this.plugin.getOptionConfig(this.plugin.fileo).getString("BossTitle");
                   pref = ChatColor.translateAlternateColorCodes('&', pref);
 
                   int maxhp = this.plugin.getConfig().getInt("Bosses." + s + ".Health");
                   int per = health * 10 / maxhp + 1;
                   int oldper = 100;
                   if (this.plugin.getCustomConfig(this.plugin.file).contains("mobs." + hitEntity.getEntityId() + ".per"))
                   {
                     oldper = this.plugin.getCustomConfig(this.plugin.file).getInt("mobs." + hitEntity.getEntityId() + ".per");
                   }
                   else
                   {
                     this.plugin.getCustomConfig(this.plugin.file).set("mobs." + hitEntity.getEntityId() + ".per", Integer.valueOf(100));
                   }
 
                   if (oldper != per)
                   {
                     for (Player player : Bukkit.getServer().getOnlinePlayers())
                     {
                       if (this.plugin.lp.getWorld() != player.getWorld())
                         continue;
                       if (this.plugin.lp.getLocation().distance(player.getLocation()) > 20.0D)
                         continue;
                       player.sendMessage(pref + ChatColor.DARK_PURPLE + k + " " + ChatColor.GRAY + "[" + ChatColor.RED + per + "0%" + ChatColor.GRAY + "]");
                     }
 
                     this.plugin.getCustomConfig(this.plugin.file).set("mobs." + hitEntity.getEntityId() + ".per", Integer.valueOf(per));
                   }
                 }
                 else
                 {
                   String s = this.plugin.getCustomConfig(this.plugin.file).getString("mobs." + hitEntity.getEntityId() + ".Name");
                   String k = s.replace("_", " ");
 
                   pref = this.plugin.getOptionConfig(this.plugin.fileo).getString("BossTitle");
                   pref = ChatColor.translateAlternateColorCodes('&', pref);
 
                   this.dmger.sendMessage(pref + ChatColor.DARK_PURPLE + k + " " + ChatColor.GRAY + "[" + ChatColor.RED + health + ChatColor.GRAY + "/" + ChatColor.RED + this.plugin.getConfig().getInt(new StringBuilder("Bosses.").append(s).append(".Health").toString()) + ChatColor.GRAY + "]");
                 }
               }
               skillexec(this.plugin.lp, this.dmger);
             }
             else if ((health > 0) && ((damager instanceof Wolf)))
             {
               int rand = (int)(Math.random() * 5.0D);
               if (rand == 0)
               {
                 damager.setVelocity(new Vector(0.0D, 1.4D, 0.0D));
               }
               if (rand == 1)
               {
                 damager.setFireTicks(100);
               }
             }
             else
             {
               String s = this.plugin.getCustomConfig(this.plugin.file).getString("mobs." + hitEntity.getEntityId() + ".Name");
               GetItems("Bosses." + s + ".Drops", this.plugin.lp);
               DeathMsg(this.plugin.lp, this.dmger);
               this.plugin.getCustomConfig(this.plugin.file).set("mobs." + hitEntity.getEntityId(), null);
               this.plugin.saveCustomConfig(this.plugin.file);
               this.plugin.lp.remove();
               if (this.plugin.getCustomConfig(this.plugin.file).contains("Timers"))
               {
                 for (String string : this.plugin.getCustomConfig(this.plugin.file).getConfigurationSection("Timers").getKeys(false))
                 {
                   if (!this.plugin.getCustomConfig(this.plugin.file).get("Timers." + string + ".id").equals(Integer.valueOf(this.plugin.lp.getEntityId())))
                     continue;
                   Integer wait = (Integer)this.plugin.getCustomConfig(this.plugin.file).get("Timers." + string + ".ticks");
                   this.plugin.getCustomConfig(this.plugin.file).set("Timers." + string + ".on", "on");
                   this.plugin.map.put(string, wait);
                 }
               }
             }
           }
         }
 
       }
       else
       {
         event.setCancelled(true);
       }
     }
   }
 
   @EventHandler(priority=EventPriority.HIGH)
   public void Leaver(PlayerQuitEvent event)
   {
     this.plugin.BossQuit(event.getPlayer());
   }
 
   public void GetItems(String s, LivingEntity f)
   {
     this.items = null;
     this.ItemList = this.plugin.getConfig().getStringList(s);
     for (String str : this.ItemList)
     {
       String[] enchant = str.split(" ");
 
       String fir = enchant[0];
 
       String[] incItems = fir.split(":");
       Integer id = parseInteger(incItems[0]);
       Integer data = parseInteger(incItems[1]);
       Integer amount = parseInteger(incItems[2]);
       int data2 = data.intValue();
 
       String sec = enchant[1];
       if (enchant.length == 3)
       {
         Integer chance = parseInteger(sec);
 
         String third = enchant[2];
         String[] Enchant = third.split(",");
         for (String enc : Enchant)
         {
           String[] last = enc.split(":");
           this.prot.add(last[0]);
           this.lvl.add(parseInteger(last[1]));
         }
         if ((id == null) || (data == null) || (amount == null))
           continue;
         ItemStack addEnchant = new ItemStack(Material.getMaterial(id.intValue()), amount.intValue(), (short)data2);
         int level = -1;
         for (??? = this.prot.iterator(); ((Iterator)???).hasNext(); ) { String st = (String)((Iterator)???).next();
 
           level++;
           addEnchant.addUnsafeEnchantment(Enchantment.getByName(st), ((Integer)this.lvl.get(level)).intValue());
         }
         drop(addEnchant, f, chance);
         this.prot.clear();
         this.lvl.clear();
       }
       else
       {
         Integer chance = parseInteger(sec);
         if ((id == null) || (data == null) || (amount == null))
           continue;
         ItemStack addEnchant = new ItemStack(Material.getMaterial(id.intValue()), amount.intValue(), (short)data2);
         drop(addEnchant, f, chance);
       }
     }
   }
 
   public void drop(ItemStack f, LivingEntity p, Integer cha)
   {
     int x = f.getAmount();
     f.setAmount(1);
     int y = 0;
     Random r = new Random();
     int random = r.nextInt(this.plugin.getOptionConfig(this.plugin.fileo).getInt("MaxPercent"));
     if (random <= cha.intValue())
     {
       while (x > y)
       {
         y++;
         p.getWorld().dropItemNaturally(p.getLocation(), f);
       }
     }
     f.setAmount(x);
   }
   private static Integer parseInteger(String s) {
     try {
       return Integer.valueOf(Integer.parseInt(s.trim()));
     } catch (Exception e) {
     }
     return null;
   }
 
   @EventHandler(priority=EventPriority.HIGH)
   public void BossNoLose(EntityDamageEvent event) {
     if ((this.plugin.getCustomConfig(this.plugin.file).contains("mobs." + event.getEntity().getEntityId())) && 
       (event.getCause() != EntityDamageEvent.DamageCause.ENTITY_ATTACK) && (event.getCause() != EntityDamageEvent.DamageCause.PROJECTILE))
     {
       event.setCancelled(true);
     }
   }
 
   public void SkillMsg(LivingEntity l, Player p)
   {
     if (this.plugin.getOptionConfig(this.plugin.fileo).contains("Message"))
     {
       if (this.plugin.getCustomConfig(this.plugin.file).contains("mobs." + l.getEntityId()))
       {
         String bossname = this.plugin.getCustomConfig(this.plugin.file).getString("mobs." + l.getEntityId() + ".Name");
         if (this.plugin.getOptionConfig(this.plugin.fileo).contains("Message." + bossname + ".SkillMsg"))
         {
           List Msgs = this.plugin.getOptionConfig(this.plugin.fileo).getStringList("Message." + bossname + ".SkillMsg");
           if (!Msgs.isEmpty())
           {
             int length = Msgs.size();
             int selected = (int)(Math.random() * length);
             String s = (String)Msgs.get(selected);
             s = ChatColor.translateAlternateColorCodes('&', s);
             for (Player player : Bukkit.getServer().getOnlinePlayers())
             {
               if (l.getWorld() != player.getWorld())
                 continue;
               if (l.getLocation().distance(player.getLocation()) > 20.0D)
                 continue;
               String t = this.plugin.getCustomConfig(this.plugin.file).getString("mobs." + l.getEntityId() + ".Name");
               t = t.replace("_", " ");
               player.sendMessage(ChatColor.DARK_PURPLE + t + " " + s);
             }
           }
         }
       }
     }
   }
 
   public void DeathMsg(LivingEntity l, Player p)
   {
     if (this.plugin.getOptionConfig(this.plugin.fileo).contains("Message"))
     {
       if (this.plugin.getCustomConfig(this.plugin.file).contains("mobs." + l.getEntityId()))
       {
         String bossname = this.plugin.getCustomConfig(this.plugin.file).getString("mobs." + l.getEntityId() + ".Name");
         String playername = p.getName();
         if (this.plugin.getOptionConfig(this.plugin.fileo).contains("Message." + bossname + ".DeathMsg"))
         {
           String dmsg = this.plugin.getOptionConfig(this.plugin.fileo).getString("Message." + bossname + ".DeathMsg");
           dmsg = ChatColor.translateAlternateColorCodes('&', dmsg);
           bossname = bossname.replace("_", " ");
           dmsg = dmsg.replace("%boss", bossname);
           dmsg = dmsg.replace("%player", playername);
           Bukkit.broadcastMessage(dmsg);
         }
         if (this.plugin.getOptionConfig(this.plugin.fileo).contains("Message." + bossname + ".CmdMsg"))
         {
           List Msgs = this.plugin.getOptionConfig(this.plugin.fileo).getStringList("Message." + bossname + ".CmdMsg");
           if (!Msgs.isEmpty())
           {
             for (String s : Msgs)
             {
               s = ChatColor.translateAlternateColorCodes('&', s);
               bossname = bossname.replace("_", " ");
               s = s.replace("%boss", bossname);
               s = s.replace("%player", playername);
               if (s.contains(":"))
               {
                 String[] parts = s.split(":");
                 int chance = Integer.parseInt(parts[0]);
                 Random r = new Random();
                 int random = r.nextInt(this.plugin.getOptionConfig(this.plugin.fileo).getInt("MaxPercent"));
                 if (random > chance)
                   continue;
                 Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), parts[1]);
               }
               else
               {
                 Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), s);
               }
             }
           }
         }
       }
     }
   }
 
   @EventHandler(priority=EventPriority.HIGH)
   public void Taming(EntityTameEvent event)
   {
     int id = event.getEntity().getEntityId();
     if (this.plugin.getCustomConfig(this.plugin.file).contains("mobs"))
     {
       if (this.plugin.getCustomConfig(this.plugin.file).contains("mobs." + id))
       {
         event.setCancelled(true);
       }
       else if (this.plugin.getCustomConfig(this.plugin.file).contains("savemobs"))
       {
         if (this.plugin.getCustomConfig(this.plugin.file).contains("savemobs." + id))
         {
           event.setCancelled(true);
         }
       }
     }
   }
 
   public void skillexec(LivingEntity l, Player p)
   {
     this.plugin.Skills(l);
     String s = this.plugin.getCustomConfig(this.plugin.file).getString("mobs." + l.getEntityId() + ".Name");
     int random = this.plugin.getConfig().getInt("Bosses." + s + ".SkillsInterval");
     if ((int)(Math.random() * random) == 0)
     {
       int cskills = this.plugin.skills.size();
       int selected = (int)(Math.random() * cskills);
       int number = 0;
       for (String str : this.plugin.skills)
       {
         if (selected == number)
         {
           SkillMsg(l, p);
           if (str.equals("teleport"))
             teleport(l, p);
           if (str.equals("swap"))
             swap(l, p);
           if (str.equals("drag_in"))
             dragin(l);
           if (str.equals("lightning")) {
             lightning(l);
           }
           if (str.equals("poison_short"))
             poisons(l, PotionEffectType.POISON, 140);
           if (str.equals("poison_long")) {
             poisons(l, PotionEffectType.POISON, 500);
           }
           if (str.equals("blindness_short"))
             poisons(l, PotionEffectType.BLINDNESS, 140);
           if (str.equals("blindness_long")) {
             poisons(l, PotionEffectType.BLINDNESS, 500);
           }
           if (str.equals("weakness_short"))
             poisons(l, PotionEffectType.WEAKNESS, 140);
           if (str.equals("weakness_long")) {
             poisons(l, PotionEffectType.WEAKNESS, 500);
           }
           if (str.equals("slow_short"))
             poisons(l, PotionEffectType.SLOW, 140);
           if (str.equals("slow_long")) {
             poisons(l, PotionEffectType.SLOW, 500);
           }
           if (str.equals("hunger_short"))
             poisons(l, PotionEffectType.HUNGER, 140);
           if (str.equals("hunger_long")) {
             poisons(l, PotionEffectType.HUNGER, 500);
           }
           if (str.equals("confusion_short"))
             poisons(l, PotionEffectType.CONFUSION, 140);
           if (str.equals("confusion_long")) {
             poisons(l, PotionEffectType.CONFUSION, 500);
           }
           if (str.equals("zombie_swarm_small"))
             spawns(l, EntityType.ZOMBIE);
           if (str.equals("zombie_swarm_big")) {
             spawnb(l, EntityType.ZOMBIE);
           }
           if (str.equals("skeleton_swarm_small"))
             spawns(l, EntityType.SKELETON);
           if (str.equals("skeleton_swarm_big"))
             spawnb(l, EntityType.SKELETON);
           number++;
         }
         else
         {
           number++;
         }
       }
     }
   }
 
   public void teleport(LivingEntity l, Player p) {
     l.teleport(p.getLocation());
   }
 
   public void dragin(LivingEntity l) {
     for (Player player : Bukkit.getServer().getOnlinePlayers())
     {
       if (l.getWorld() != player.getWorld())
         continue;
       if (l.getLocation().distance(player.getLocation()) > 20.0D)
         continue;
       player.teleport(l.getLocation());
     }
   }
 
   public void swap(LivingEntity l, Player p)
   {
     Location lo = l.getLocation();
     l.teleport(p.getLocation());
     p.teleport(lo);
   }
 
   public void lightning(LivingEntity l) {
     for (Player player : Bukkit.getServer().getOnlinePlayers())
     {
       if (l.getWorld() != player.getWorld())
         continue;
       if (l.getLocation().distance(player.getLocation()) > 20.0D)
         continue;
       player.getWorld().strikeLightning(player.getLocation());
     }
   }
 
   public void fires(LivingEntity l)
   {
     for (Player player : Bukkit.getServer().getOnlinePlayers())
     {
       if (l.getWorld() != player.getWorld())
         continue;
       if (l.getLocation().distance(player.getLocation()) > 20.0D)
         continue;
       player.setFireTicks(140);
     }
   }
 
   public void firel(LivingEntity l)
   {
     for (Player player : Bukkit.getServer().getOnlinePlayers())
     {
       if (l.getWorld() != player.getWorld())
         continue;
       if (l.getLocation().distance(player.getLocation()) > 20.0D)
         continue;
       player.setFireTicks(500);
     }
   }
 
   public void poisons(LivingEntity l, PotionEffectType p, int q)
   {
     for (Player player : Bukkit.getServer().getOnlinePlayers())
     {
       if (l.getWorld() != player.getWorld())
         continue;
       if (l.getLocation().distance(player.getLocation()) > 20.0D)
         continue;
       player.addPotionEffect(new PotionEffect(p, q, 2));
     }
   }
 
   public void spawns(LivingEntity l, EntityType t)
   {
     l.getWorld().spawnEntity(l.getLocation(), t);
     l.getWorld().spawnEntity(l.getLocation(), t);
     l.getWorld().spawnEntity(l.getLocation(), t);
     l.getWorld().spawnEntity(l.getLocation(), t);
     l.getWorld().spawnEntity(l.getLocation(), t);
   }
 
   public void spawnb(LivingEntity l, EntityType t) {
     l.getWorld().spawnEntity(l.getLocation(), t);
     l.getWorld().spawnEntity(l.getLocation(), t);
     l.getWorld().spawnEntity(l.getLocation(), t);
     l.getWorld().spawnEntity(l.getLocation(), t);
     l.getWorld().spawnEntity(l.getLocation(), t);
     l.getWorld().spawnEntity(l.getLocation(), t);
     l.getWorld().spawnEntity(l.getLocation(), t);
     l.getWorld().spawnEntity(l.getLocation(), t);
     l.getWorld().spawnEntity(l.getLocation(), t);
     l.getWorld().spawnEntity(l.getLocation(), t);
     l.getWorld().spawnEntity(l.getLocation(), t);
     l.getWorld().spawnEntity(l.getLocation(), t);
     l.getWorld().spawnEntity(l.getLocation(), t);
     l.getWorld().spawnEntity(l.getLocation(), t);
     l.getWorld().spawnEntity(l.getLocation(), t);
   }
 }
