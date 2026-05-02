-- MySQL dump 10.13  Distrib 8.0.44, for Win64 (x86_64)
--
-- Host: the-backroom-gabaslander-a0df.f.aivencloud.com    Database: defaultdb
-- ------------------------------------------------------
-- Server version	8.0.45

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

-- Create the Database
DROP DATABASE IF EXISTS `thebackroom_db`;
CREATE DATABASE `thebackroom_db`;
USE `thebackroom_db`;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `category` (
  `category_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  PRIMARY KEY (`category_id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=74 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (9,'Action'),(73,'Action RPG'),(3,'Adventure'),(29,'Anime'),(10,'Apocalyptic'),(62,'Base-Building'),(31,'Boy’s Love'),(19,'Boys\' Love'),(2,'Children’s Literature'),(61,'City-Building'),(13,'Classic'),(25,'Comedy'),(15,'Coming of Age'),(22,'Contemporary Fiction'),(34,'Dark Comedy'),(35,'Disaster'),(21,'Drama'),(16,'Dystopian'),(70,'Esports'),(36,'Family'),(1,'Fantasy'),(72,'Farming'),(23,'Feminism'),(6,'Fiction'),(59,'Gacha'),(26,'Historical'),(20,'Historical Fiction'),(7,'Horror'),(28,'Isekai'),(24,'LGBTQ+'),(66,'Life Simulation'),(5,'Light Novel'),(14,'Literature'),(63,'MMO'),(68,'MOBA'),(69,'Multiplayer'),(4,'Mystery'),(64,'Online'),(56,'Open World'),(11,'Psychological'),(12,'Romance'),(55,'RPG'),(67,'Sandbox'),(27,'Satire'),(58,'Sci-Fi'),(17,'Science Fiction'),(65,'Simulation'),(30,'Slice of Life'),(8,'Steampunk'),(60,'Strategy'),(32,'Supernatural'),(71,'Survival'),(33,'Thriller'),(57,'Turn-Based RPG'),(18,'Xianxia');
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `company`
--

DROP TABLE IF EXISTS `company`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `company` (
  `company_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`company_id`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `company`
--

LOCK TABLES `company` WRITE;
/*!40000 ALTER TABLE `company` DISABLE KEYS */;
INSERT INTO `company` VALUES (1,'Bloomsbury'),(2,'Scholastic'),(3,'Yen Press'),(4,'Ize Press'),(5,'T. Egerton'),(6,'Roberts Brothers'),(7,'Ballantine Books'),(8,'Seven Seas Entertainment'),(9,'Jinjiang Literature City'),(10,'Atria Books'),(11,'Doubleday'),(12,'Mainichi Broadcasting System (MBS)'),(13,'Tomorrow Studios'),(14,'Netflix'),(15,'Thruline Entertainment'),(16,'Hulu Originals'),(17,'J.C.Staff'),(18,'Drive Inc.'),(19,'Calendar Studios'),(20,'Overall Pictures'),(21,'Barunson E&A'),(22,'Hwansang Studio'),(23,'Columbia Pictures'),(24,'110'),(25,'Star Cinema'),(26,'HoYoverse'),(27,'Devsisters Studio Kingdom'),(28,'Devsisters'),(29,'Supercell'),(30,'Maxis'),(31,'Electronic Arts'),(32,'Riot Games'),(33,'Mojang Studios'),(34,'Microsoft Studios'),(35,'ConcernedApe'),(36,'CD Projekt Red'),(37,'CD Projekt'),(38,'Scott Cawthon');
/*!40000 ALTER TABLE `company` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary view structure for view `login_view`
--

DROP TABLE IF EXISTS `login_view`;
/*!50001 DROP VIEW IF EXISTS `login_view`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `login_view` AS SELECT 
 1 AS `username`,
 1 AS `password`,
 1 AS `role`*/;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `media`
--

DROP TABLE IF EXISTS `media`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `media` (
  `media_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `release_year` varchar(4) NOT NULL,
  `synopsis` text,
  `media_type` enum('Book','Movie','TvShow','Game') NOT NULL,
  `icon_path` varchar(500) DEFAULT NULL,
  `isbn` varchar(20) DEFAULT NULL,
  `page_count` varchar(10) DEFAULT NULL,
  `edition` varchar(50) DEFAULT NULL,
  `duration` varchar(50) DEFAULT NULL,
  `language` varchar(100) DEFAULT NULL,
  `season_count` varchar(10) DEFAULT NULL,
  `episode_count` varchar(10) DEFAULT NULL,
  `status` enum('Completed','Ongoing','Discontinued') DEFAULT 'Ongoing',
  `game_engine` varchar(100) DEFAULT NULL,
  `system_requirements` text,
  PRIMARY KEY (`media_id`),
  UNIQUE KEY `uniq_media` (`name`,`media_type`,`release_year`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `media`
--

LOCK TABLES `media` WRITE;
/*!40000 ALTER TABLE `media` DISABLE KEYS */;
INSERT INTO `media` VALUES (1,'Harry Potter and the Philosopher\'s Stone','1997','\"Turning the envelope over, his hand trembling, Harry saw a purple wax seal bearing a coat of arms; a lion, an eagle, a badger and a snake surrounding a large letter \'H\'. \" Harry Potter has never even heard of Hogwarts when the letters start dropping on the doormat at number four, Privet Drive. Addressed in green ink on yellowish parchment with a purple seal, they are swiftly confiscated by his grisly aunt and uncle. Then, on Harry\'s eleventh birthday, a great beetle-eyed giant of a man called Rubeus Hagrid bursts in with some astonishing news: Harry Potter is a wizard, and he has a place at Hogwarts School of Witchcraft and Wizardry. An incredible adventure is about to begin!','Book','uploads/mediaIcon/1777176703921.jpg','9780747532699','309','1st Edition',NULL,NULL,NULL,NULL,'Ongoing',NULL,NULL),(2,'Lord of Mysteries Volume 1','2021','With the rising tide of steam power and machinery, who can come close to being a Beyonder? Shrouded in the fog of history and darkness, who or what is the lurking evil that murmurs into our ears? Waking up to be faced with a string of mysteries, Zhou Mingrui finds himself reincarnated as Klein Moretti in an alternate Victorian era world where he sees a world filled with machinery, cannons, dreadnoughts, airships, difference machines, as well as Potions, Divination, Hexes, Tarot Cards, Sealed Artifacts…The Light continues to shine but mystery has never gone far. Follow Klein as he finds himself entangled with the Churches of the world—both orthodox and unorthodox—while he slowly develops newfound powers thanks to the Beyonder potions. Like the corresponding tarot card, The Fool, which is numbered 0—a number of unlimited potential—this is the legend of \"The Fool.\"','Book','uploads/mediaIcon/1777176708089.jpg','9798855413779','1165','Kindle Edition',NULL,NULL,NULL,NULL,'Ongoing',NULL,NULL),(3,'Omniscient Reader\'s Viewpoint Volume 1','2025','IF YOU ARE READING THIS, YOU WILL SURVIVE.Kill each other within the time limit or die. It’s just another evening commute on the train, until the passengers are given an order they can’t disobey. Utter chaos ensues, but ordinary office worker Dokja Kim only feels an unsettling calm. He knows exactly how this will play out! The subway car, the passengers’ reactions, even the bizarre creature that suddenly appears to oversee this sadistic scenario...everything is straight out of his favorite story, an online novel so obscure he is its sole reader. And as the only one who knows where the plot is headed, Dokja must use his knowledge to survive the oncoming apocalypse!','Book','uploads/mediaIcon/1777176712782.jpg','9788932921006','252','1st Edition',NULL,NULL,NULL,NULL,'Ongoing',NULL,NULL),(4,'Pride and Prejudice','1813','\"It is a truth universally acknowledged, that a single man in possession of a good fortune, must be in want of a wife.\" The novel follows Elizabeth Bennet as she deals with issues of manners, upbringing, morality, education, and marriage in the society of the British Regency. As she encounters the proud Mr. Darcy, misunderstandings and personal growth lead both characters toward a deeper understanding of themselves and each other.','Book','uploads/mediaIcon/1777176715019.jpg','9780141439518','432','1st Edition',NULL,NULL,NULL,NULL,'Ongoing',NULL,NULL),(5,'Little Women','1868','\"Christmas won\'t be Christmas without any presents,\" grumbled Jo, lying on the rug. The novel follows the lives of the four March sisters—Meg, Jo, Beth, and Amy—as they grow up during the American Civil War, learning about love, hardship, ambition, and the importance of family.','Book','uploads/mediaIcon/1777176717525.jpg','9780147514011','449','Penguin Classics',NULL,NULL,NULL,NULL,'Ongoing',NULL,NULL),(6,'Fahrenheit 451','1953','It was a pleasure to burn. In a future society where books are outlawed and firemen burn any that are found, Guy Montag begins to question everything he has ever known after meeting a young neighbor who opens his eyes to a different way of thinking.','Book','uploads/mediaIcon/1777176719522.jpg','9781451673319','256','Mass Market Paperback Edition',NULL,NULL,NULL,NULL,'Ongoing',NULL,NULL),(7,'Heaven Official’s Blessing Volume 1','2021','Body in abyss, heart in paradise. The story follows Xie Lian, a once-crowned prince who ascends to godhood for the third time, only to be cast down again and sent on missions in the mortal realm, where he encounters the mysterious Hua Cheng and begins a journey involving gods, ghosts, and fate.','Book','uploads/mediaIcon/1777176721846.jpg','9781648279171','408','English Edition (Volume 1)',NULL,NULL,NULL,NULL,'Ongoing',NULL,NULL),(8,'The Seven Husbands of Evelyn Hugo','2017','\"Don\'t ignore half of me so you can fit me into a box. Don\'t do that.\" Reclusive Hollywood icon Evelyn Hugo finally decides to tell her life story to an unknown magazine reporter, Monique Grant, revealing a tale of ambition, love, secrets, and the price of fame across decades in the spotlight.','Book','uploads/mediaIcon/1777176724462.jpg','9781501161933','400','First Atria Paperback Edition',NULL,NULL,NULL,NULL,'Ongoing',NULL,NULL),(9,'Lessons in Chemistry','2022','Courage is the root of change—and change is what we’re chemically designed to do. Set in the 1960s, the story follows Elizabeth Zott, a brilliant chemist who becomes an unlikely television cooking show host, challenging societal norms and inspiring women to change the status quo.','Book','uploads/mediaIcon/1777176726360.jpg','9780385547345','400','First Edition',NULL,NULL,NULL,NULL,'Ongoing',NULL,NULL),(10,'Grandmaster of Demonic Cultivation Vol. 1','2021','The patriarch of the demonic path, Wei Wuxian, has been reviled for his unorthodox methods—but when he is summoned back to life, a new mystery begins to unfold. The story follows Wei Wuxian as he returns from death and reunites with Lan Wangji, uncovering dark secrets tied to his past and the cultivation world.','Book','uploads/mediaIcon/1777176729237.jpg','9781648279195','416','English Edition (Volume 1)',NULL,NULL,NULL,NULL,'Ongoing',NULL,NULL),(11,'My Beautiful Man','2021','A quiet, socially awkward high school boy becomes obsessively devoted to a popular classmate, leading to a complex and emotionally intense relationship that evolves over time.','TvShow','uploads/mediaIcon/1777186440017._V1_.jpg',NULL,NULL,NULL,NULL,NULL,'2','6','Completed',NULL,NULL),(12,'One Piece (Live Action)','2023','A young pirate, Monkey D. Luffy, sets sail with his crew to find the legendary One Piece treasure while facing powerful enemies and forming unbreakable bonds.','TvShow','uploads/mediaIcon/1777186443225._V1_.jpg',NULL,NULL,NULL,NULL,NULL,'2','16','Ongoing',NULL,NULL),(13,'The Great','2020','A satirical, semi-fictional retelling of Catherine the Great’s rise to power in Russia as she navigates love, betrayal, and political chaos.','TvShow','uploads/mediaIcon/1777186447075._V1_FMjpg_UX1000_.jpg',NULL,NULL,NULL,NULL,NULL,'3','30','Completed',NULL,NULL),(14,'Tsukimichi: Moonlit Fantasy','2021','A high school student is summoned to a fantasy world as a hero but is rejected by the goddess and instead builds his own path with powerful abilities and allies.','TvShow','uploads/mediaIcon/1777186450746._V1_.jpg',NULL,NULL,NULL,NULL,NULL,'2','37','Ongoing',NULL,NULL),(15,'Go for It, Nakamura!','2026','A shy high school boy secretly loves his classmate Nakamura and struggles with his feelings while trying to get closer to him in a comedic coming-of-age story.','TvShow','uploads/mediaIcon/1777186453902._V1_FMjpg_UX1000_.jpg',NULL,NULL,NULL,NULL,NULL,'1','12','Ongoing',NULL,NULL),(16,'Marry My Dead Body','2023','A homophobic police officer accidentally picks up a red envelope marriage ritual and ends up \'married\' to a ghost, leading them to solve crimes together while clashing and bonding along the way.','Movie','uploads/mediaIcon/1777188309423._V1_.jpg',NULL,NULL,NULL,'129','Chinese (Mandarin)',NULL,NULL,'Ongoing',NULL,NULL),(17,'Parasite','2019','A poor family schemes to infiltrate a wealthy household, but an unexpected turn of events escalates the situation into a dark and intense clash between social classes.','Movie','uploads/mediaIcon/1777188878312._V1_.jpg',NULL,NULL,NULL,'132','Korean',NULL,NULL,'Ongoing',NULL,NULL),(18,'The Great Flood','2025','A catastrophic global flood traps an AI researcher and her young son in a submerged apartment building, where she must fight to survive while becoming entangled in a mission critical to humanity’s future.','Movie','uploads/mediaIcon/1777188881089._V1_FMjpg_UX1000_.jpg',NULL,NULL,NULL,'108','Korean',NULL,NULL,'Ongoing',NULL,NULL),(19,'Mona Lisa Smile','2003','In 1953, an art history professor at a conservative women’s college challenges her students to rethink traditional roles and expectations in life and society.','Movie','uploads/mediaIcon/1777188883444._V1_.jpg',NULL,NULL,NULL,'117','English',NULL,NULL,'Ongoing',NULL,NULL),(23,'Girl Boy Bakla Tomboy','2013','Four siblings with different gender identities are reunited when their estranged father becomes ill, forcing them to confront identity, acceptance, and family bonds in a comedic yet emotional story.','Movie','uploads/mediaIcon/1777190034043.jpg',NULL,NULL,NULL,'110','Filipino (Tagalog)',NULL,NULL,'Ongoing',NULL,NULL),(24,'Genshin Impact','2020','An open-world action RPG set in the fantasy world of Teyvat, where players explore diverse regions, collect characters with unique elemental abilities, and uncover the story of the Traveler while battling enemies and completing quests.','Game','uploads/mediaIcon/1777274730392.jpg',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'Ongoing','Unity','Minimum: Intel Core i5 / 8GB RAM / NVIDIA GT 1030 / 30GB storage / Windows 7 SP1 64-bit; Recommended: Intel Core i7 / 16GB RAM / NVIDIA GTX 1060 6GB / SSD with 30GB+ free / Windows 10 64-bit'),(25,'Honkai: Star Rail','2023','A free-to-play turn-based RPG set in a sci-fi fantasy universe where players board the Astral Express to travel across diverse worlds, uncover hidden truths about the Stellaron crisis, and engage in strategic combat with a cast of unique characters.','Game','uploads/mediaIcon/1777274737194.png',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'Ongoing','Proprietary Engine','Minimum: Intel Core i5 / 8GB RAM / NVIDIA GTX 970 / 20GB storage / Windows 10 64-bit Recommended: Intel Core i7 / 16GB RAM / NVIDIA GTX 1060 or better / SSD with 20GB+ free space / Windows 10 64-bit'),(26,'Cookie Run: Kingdom','2021','A free-to-play kingdom-building RPG where players collect and upgrade cookies, construct and customize their kingdom, and engage in real-time battles in a colorful fantasy world.','Game','uploads/mediaIcon/1777274740723.png',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'Ongoing','Proprietary Engine','Minimum: Android 5.0+ / iOS 11.0+ Recommended: Mid-range or higher devices for smooth performance'),(27,'Clash of Clans','2012','A free-to-play online strategy game where players build and upgrade villages, train armies, and attack other players\' bases in asynchronous multiplayer battles to earn resources and dominate clans.','Game','uploads/mediaIcon/1777274744307._V1_.jpg',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'Ongoing','Proprietary Engine','Varies by device'),(28,'The Sims 4','2014','A life simulation game where players create and control virtual people (Sims), build homes, and shape their lives, relationships, and careers in an open-ended sandbox experience.','Game','uploads/mediaIcon/1777274747276._V1_FMjpg_UX1200_.jpg',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'Ongoing','Frostbite Engine','Minimum: Intel Core i3 / 4GB RAM / GTX 650 / 15GB storage / Windows 10 64-bit Recommended: Intel Core i5 / 8GB RAM / GTX 660 or better / 18GB+ storage / Windows 10 64-bit'),(29,'League of Legends','2009','A free-to-play competitive multiplayer online battle arena (MOBA) game where teams of champions battle to destroy the enemy Nexus using strategy, teamwork, and character-based abilities.','Game','uploads/mediaIcon/1777274752593.jpg',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'Ongoing','Proprietary Engine','Minimum: Intel Core i3 / 4GB RAM / NVIDIA GeForce 8800 / 12GB storage / Windows 10 Recommended: Intel Core i5 / 8GB RAM / GTX 560 or better / SSD recommended / Windows 10'),(30,'Minecraft','2011','A sandbox game where players can build, explore, and survive in a procedurally generated block-based world with infinite possibilities, including creative building and survival gameplay.','Game','uploads/mediaIcon/1777274755195.png',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'Ongoing','Java Edition:No Engine (Custom Java Implementation)','Minimum: Intel Core i3 / 4GB RAM / Intel HD Graphics 4000 / 1GB storage / Windows 10 (Java Edition varies by version) Recommended: Intel Core i5 / 8GB RAM / Dedicated GPU / SSD recommended'),(31,'Stardew Valley','2016','A farming simulation RPG where players inherit a neglected farm and build a new life through farming, relationships, mining, fishing, and exploration in a charming rural town.','Game','uploads/mediaIcon/1777274757502.png',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'Ongoing','MonoGame','Varies by platform'),(32,'Cyberpunk 2077','2020','An open-world action RPG set in a dystopian futuristic city where players take on the role of V, navigating Night City filled with crime, advanced technology, and moral choices that shape the story.','Game','uploads/mediaIcon/1777274760785.jpg',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'Ongoing','REDengine 4','Minimum: Intel Core i5 / 8GB RAM / NVIDIA GTX 780 or AMD equivalent / 70GB storage / Windows 10 64-bit Recommended: Intel Core i7 / 12GB RAM / NVIDIA GTX 1060 or better / SSD recommended / Windows 10 64-bit'),(33,'Five Nights at Freddy\'s 1','2014','A point-and-click survival horror game where players work as a night security guard at Freddy Fazbear\'s Pizza, monitoring cameras and surviving against hostile animatronic characters.','Game','uploads/mediaIcon/1777274952883.jpg',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'Ongoing','Clickteam Fusion','Minimum: Intel or AMD 2.0 GHz CPU / 1GB RAM / Integrated graphics / 250MB storage / Windows XP or later Recommended: Dual-core CPU / 2GB RAM / Dedicated or modern integrated GPU / 250MB storage / Windows 7 or later');
/*!40000 ALTER TABLE `media` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `media_access`
--

DROP TABLE IF EXISTS `media_access`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `media_access` (
  `media_id` int NOT NULL,
  `website_id` int NOT NULL,
  `url` varchar(500) NOT NULL,
  PRIMARY KEY (`media_id`,`website_id`),
  KEY `website_id` (`website_id`),
  CONSTRAINT `media_access_ibfk_1` FOREIGN KEY (`media_id`) REFERENCES `media` (`media_id`) ON DELETE CASCADE,
  CONSTRAINT `media_access_ibfk_2` FOREIGN KEY (`website_id`) REFERENCES `website` (`website_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `media_access`
--

LOCK TABLES `media_access` WRITE;
/*!40000 ALTER TABLE `media_access` DISABLE KEYS */;
INSERT INTO `media_access` VALUES (1,1,'https://www.wizardingworld.com'),(1,2,'https://www.bloomsbury.com/uk/harry-potter/'),(1,3,'https://harrypotter.fandom.com/wiki/Harry_Potter_and_the_Philosopher%27s_Stone'),(1,4,'https://www.goodreads.com/book/show/3.Harry_Potter_and_the_Sorcerer_s_Stone'),(2,4,'https://www.goodreads.com/book/show/58826678-lord-of-the-mysteries-volume-1?ref=nav_sb_ss_1_12'),(3,4,'https://www.goodreads.com/book/show/220520518-omniscient-reader-s-viewpoint-vol-1?ref=nav_sb_ss_1_10'),(4,6,'https://www.gutenberg.org/ebooks/1342'),(4,7,'https://www.goodreads.com/book/show/1885.Pride_and_Prejudice'),(5,7,'https://www.goodreads.com/book/show/1934.Little_Women'),(6,7,'https://www.goodreads.com/book/show/13079982-fahrenheit-451'),(6,8,'https://raybradbury.com'),(7,7,'https://www.goodreads.com/book/show/58701909-heaven-official-s-blessing-vol-1'),(7,9,'https://sevenseasentertainment.com/series/heaven-officials-blessing/'),(8,7,'https://www.goodreads.com/book/show/32620332-the-seven-husbands-of-evelyn-hugo'),(8,8,'https://taylorjenkinsreid.com'),(9,7,'https://www.goodreads.com/book/show/58065033-lessons-in-chemistry'),(9,8,'https://www.bonniegarmus.com'),(10,7,'https://www.goodreads.com/book/show/58701673-grandmaster-of-demonic-cultivation-vol-1'),(10,9,'https://sevenseasentertainment.com/series/grandmaster-of-demonic-cultivation/'),(11,10,'https://www.imdb.com/title/tt15529096/'),(11,11,'https://en.wikipedia.org/wiki/My_Beautiful_Man'),(12,12,'https://www.netflix.com/title/80217863'),(13,13,'https://www.hulu.com/series/the-great'),(14,14,'https://www.crunchyroll.com/series/GZJH3D719/tsukimichi--moonlit-fantasy-?srsltid=AfmBOoqqX7MDdvaF65X2hktiOw35UWnJUGzbVI9rg8Oa3x2f2g6MHF13'),(15,7,'https://www.goodreads.com/book/show/36247159-go-for-it-nakamura'),(16,10,'https://www.imdb.com/title/tt27502389/'),(16,11,'https://en.wikipedia.org/wiki/Marry_My_Dead_Body'),(17,10,'https://www.imdb.com/title/tt6751668/'),(17,11,'https://en.wikipedia.org/wiki/Parasite_(film)'),(18,10,'https://www.imdb.com/title/tt29927663/'),(18,12,'https://www.netflix.com/title/81579978'),(19,10,'https://www.imdb.com/title/tt0304415/'),(19,11,'https://en.wikipedia.org/wiki/Mona_Lisa_Smile'),(23,10,'https://www.imdb.com/title/tt2996804/'),(23,11,'https://en.wikipedia.org/wiki/Girl,_Boy,_Bakla,_Tomboy'),(24,22,'https://genshin.hoyoverse.com/'),(25,22,'https://hsr.hoyoverse.com/'),(26,23,'https://www.cookierun-kingdom.com/en/'),(27,24,'https://supercell.com/en/games/clashofclans/'),(28,25,'https://www.ea.com/games/the-sims/the-sims-4'),(29,26,'https://www.leagueoflegends.com/'),(30,27,'https://www.minecraft.net/'),(31,28,'https://store.steampowered.com/app/413150/Stardew_Valley/'),(32,28,'https://store.steampowered.com/app/1091500/Cyberpunk_2077/'),(33,28,'https://store.steampowered.com/app/319510/Five_Nights_at_Freddys/');
/*!40000 ALTER TABLE `media_access` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `media_category`
--

DROP TABLE IF EXISTS `media_category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `media_category` (
  `media_id` int NOT NULL,
  `category_id` int NOT NULL,
  PRIMARY KEY (`media_id`,`category_id`),
  KEY `category_id` (`category_id`),
  CONSTRAINT `media_category_ibfk_1` FOREIGN KEY (`media_id`) REFERENCES `media` (`media_id`) ON DELETE CASCADE,
  CONSTRAINT `media_category_ibfk_2` FOREIGN KEY (`category_id`) REFERENCES `category` (`category_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `media_category`
--

LOCK TABLES `media_category` WRITE;
/*!40000 ALTER TABLE `media_category` DISABLE KEYS */;
INSERT INTO `media_category` VALUES (1,1),(2,1),(3,1),(7,1),(10,1),(12,1),(14,1),(25,1),(1,2),(1,3),(12,3),(14,3),(24,3),(25,3),(30,3),(2,4),(16,4),(2,5),(2,6),(5,6),(2,7),(33,7),(2,8),(2,9),(3,9),(10,9),(12,9),(24,9),(3,10),(3,11),(4,12),(7,12),(8,12),(11,12),(15,12),(19,12),(4,13),(5,13),(6,13),(4,14),(5,15),(6,16),(6,17),(18,17),(7,18),(10,18),(7,19),(10,19),(8,20),(9,20),(8,21),(9,21),(11,21),(13,21),(17,21),(19,21),(23,21),(9,22),(9,23),(11,24),(16,24),(23,24),(13,25),(15,25),(16,25),(23,25),(13,26),(19,26),(13,27),(14,28),(14,29),(15,29),(15,30),(15,31),(16,32),(17,33),(18,33),(17,34),(18,35),(23,36),(24,55),(26,55),(31,55),(24,56),(32,56),(25,57),(25,58),(32,58),(25,59),(26,59),(26,60),(27,60),(29,60),(26,61),(27,62),(27,63),(27,64),(28,65),(31,65),(28,66),(31,66),(28,67),(30,67),(29,68),(29,69),(29,70),(30,71),(33,71),(31,72),(32,73);
/*!40000 ALTER TABLE `media_category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary view structure for view `media_category_overview`
--

DROP TABLE IF EXISTS `media_category_overview`;
/*!50001 DROP VIEW IF EXISTS `media_category_overview`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `media_category_overview` AS SELECT 
 1 AS `media_type`,
 1 AS `category_name`*/;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `media_company`
--

DROP TABLE IF EXISTS `media_company`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `media_company` (
  `media_id` int NOT NULL,
  `company_id` int NOT NULL,
  `role_id` int NOT NULL,
  PRIMARY KEY (`media_id`,`company_id`,`role_id`),
  KEY `company_id` (`company_id`),
  KEY `role_id` (`role_id`),
  CONSTRAINT `media_company_ibfk_1` FOREIGN KEY (`media_id`) REFERENCES `media` (`media_id`) ON DELETE CASCADE,
  CONSTRAINT `media_company_ibfk_2` FOREIGN KEY (`company_id`) REFERENCES `company` (`company_id`) ON DELETE CASCADE,
  CONSTRAINT `media_company_ibfk_3` FOREIGN KEY (`role_id`) REFERENCES `role` (`role_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `media_company`
--

LOCK TABLES `media_company` WRITE;
/*!40000 ALTER TABLE `media_company` DISABLE KEYS */;
INSERT INTO `media_company` VALUES (1,1,2),(1,2,2),(2,3,2),(3,4,2),(4,5,2),(5,6,2),(6,7,2),(7,8,2),(10,8,2),(7,9,2),(10,9,2),(8,10,2),(9,11,2),(11,12,4),(12,13,4),(12,14,4),(18,14,4),(13,15,4),(13,16,4),(14,17,4),(15,18,4),(16,19,4),(16,20,4),(17,21,4),(18,22,4),(19,23,4),(23,25,4),(24,26,2),(24,26,7),(25,26,2),(25,26,7),(26,27,7),(26,28,2),(27,29,2),(27,29,7),(28,30,7),(28,31,2),(29,32,2),(29,32,7),(30,33,7),(30,34,2),(31,35,2),(31,35,7),(32,36,7),(32,37,2),(33,38,2);
/*!40000 ALTER TABLE `media_company` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `media_game_mode`
--

DROP TABLE IF EXISTS `media_game_mode`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `media_game_mode` (
  `media_id` int NOT NULL,
  `mode_id` int NOT NULL,
  PRIMARY KEY (`media_id`,`mode_id`),
  KEY `mode_id` (`mode_id`),
  CONSTRAINT `media_game_mode_ibfk_1` FOREIGN KEY (`media_id`) REFERENCES `media` (`media_id`) ON DELETE CASCADE,
  CONSTRAINT `media_game_mode_ibfk_2` FOREIGN KEY (`mode_id`) REFERENCES `mode` (`mode_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `media_game_mode`
--

LOCK TABLES `media_game_mode` WRITE;
/*!40000 ALTER TABLE `media_game_mode` DISABLE KEYS */;
INSERT INTO `media_game_mode` VALUES (24,1),(25,1),(26,1),(28,1),(30,1),(31,1),(32,1),(33,1),(24,2),(24,3),(25,4),(26,5),(27,5),(29,5),(30,5),(31,5);
/*!40000 ALTER TABLE `media_game_mode` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `media_game_platform`
--

DROP TABLE IF EXISTS `media_game_platform`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `media_game_platform` (
  `media_id` int NOT NULL,
  `platform_id` int NOT NULL,
  PRIMARY KEY (`media_id`,`platform_id`),
  KEY `platform_id` (`platform_id`),
  CONSTRAINT `media_game_platform_ibfk_1` FOREIGN KEY (`media_id`) REFERENCES `media` (`media_id`) ON DELETE CASCADE,
  CONSTRAINT `media_game_platform_ibfk_2` FOREIGN KEY (`platform_id`) REFERENCES `platform` (`platform_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `media_game_platform`
--

LOCK TABLES `media_game_platform` WRITE;
/*!40000 ALTER TABLE `media_game_platform` DISABLE KEYS */;
INSERT INTO `media_game_platform` VALUES (24,1),(25,1),(28,1),(29,1),(31,1),(32,1),(33,1),(24,2),(31,2),(33,2),(24,3),(32,3),(24,4),(25,4),(32,4),(25,5),(26,5),(27,5),(33,5),(25,6),(26,6),(27,6),(33,6),(28,7),(28,8),(28,9),(30,10),(31,11),(33,11),(32,12),(32,13);
/*!40000 ALTER TABLE `media_game_platform` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `media_personnel`
--

DROP TABLE IF EXISTS `media_personnel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `media_personnel` (
  `media_id` int NOT NULL,
  `person_id` int NOT NULL,
  `role_id` int NOT NULL,
  PRIMARY KEY (`media_id`,`person_id`,`role_id`),
  KEY `person_id` (`person_id`),
  KEY `role_id` (`role_id`),
  CONSTRAINT `media_personnel_ibfk_1` FOREIGN KEY (`media_id`) REFERENCES `media` (`media_id`) ON DELETE CASCADE,
  CONSTRAINT `media_personnel_ibfk_2` FOREIGN KEY (`person_id`) REFERENCES `person` (`person_id`) ON DELETE CASCADE,
  CONSTRAINT `media_personnel_ibfk_3` FOREIGN KEY (`role_id`) REFERENCES `role` (`role_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `media_personnel`
--

LOCK TABLES `media_personnel` WRITE;
/*!40000 ALTER TABLE `media_personnel` DISABLE KEYS */;
INSERT INTO `media_personnel` VALUES (1,1,1),(2,2,1),(3,3,1),(4,4,1),(5,5,1),(6,6,1),(7,7,1),(10,7,1),(8,8,1),(9,9,1),(11,10,3),(12,11,3),(12,12,3),(12,13,3),(13,14,3),(14,15,3),(15,16,3),(16,17,3),(17,18,3),(18,19,3),(19,20,3),(23,31,3),(33,32,6);
/*!40000 ALTER TABLE `media_personnel` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mode`
--

DROP TABLE IF EXISTS `mode`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `mode` (
  `mode_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`mode_id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mode`
--

LOCK TABLES `mode` WRITE;
/*!40000 ALTER TABLE `mode` DISABLE KEYS */;
INSERT INTO `mode` VALUES (3,'Gacha'),(5,'Multiplayer'),(4,'Online'),(2,'Online Multiplayer'),(1,'Single-player');
/*!40000 ALTER TABLE `mode` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `person`
--

DROP TABLE IF EXISTS `person`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `person` (
  `person_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`person_id`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `person`
--

LOCK TABLES `person` WRITE;
/*!40000 ALTER TABLE `person` DISABLE KEYS */;
INSERT INTO `person` VALUES (1,'J.K. Rowling'),(2,'Cuttlefish That Loves Diving'),(3,'singNsong'),(4,'Jane Austen'),(5,'Louisa May Alcott'),(6,'Ray Bradbury'),(7,'Mo Xiang Tong Xiu'),(8,'Taylor Jenkins Reid'),(9,'Bonnie Garmus'),(10,'Makoto Satō'),(11,'Marc Jobst'),(12,'Tim Southam'),(13,'Emma Sullivan'),(14,'Tony McNamara'),(15,'Kenta Ihara'),(16,'NAOYA TADATOSHI'),(17,'Cheng Wei-hao'),(18,'Bong Joon-ho'),(19,'Kim Byung-woo'),(20,'Mike Newell'),(21,'_Tomboy'),(22,'Cheng Wei-hao'),(23,'_Tomboy'),(24,'Bong Joon-ho'),(25,'Kim Byung-woo'),(26,'Mike Newell'),(27,'Makoto Satō'),(28,'Marc Jobst'),(29,'Tony McNamara'),(30,'Kenta Ihara'),(31,'Wenn V. Deramas'),(32,'Scott Cawthon');
/*!40000 ALTER TABLE `person` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `platform`
--

DROP TABLE IF EXISTS `platform`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `platform` (
  `platform_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  PRIMARY KEY (`platform_id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `platform`
--

LOCK TABLES `platform` WRITE;
/*!40000 ALTER TABLE `platform` DISABLE KEYS */;
INSERT INTO `platform` VALUES (1,'PC'),(2,'Mobile'),(3,'PlayStation 4'),(4,'PlayStation 5'),(5,'Android'),(6,'iOS'),(7,'macOS'),(8,'PlayStation'),(9,'Xbox'),(10,'Xbox Game Studios'),(11,'Console'),(12,'Xbox One'),(13,'Xbox Series X/S');
/*!40000 ALTER TABLE `platform` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role` (
  `role_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,'Author'),(2,'Publisher'),(3,'Director'),(4,'Production Studio'),(6,'Game Developer'),(7,'Game Studio');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `password` varchar(255) NOT NULL,
  `role` varchar(20) NOT NULL DEFAULT 'MEMBER',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `username` (`username`),
  UNIQUE KEY `username_2` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `website`
--

DROP TABLE IF EXISTS `website`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `website` (
  `website_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  PRIMARY KEY (`website_id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `website`
--

LOCK TABLES `website` WRITE;
/*!40000 ALTER TABLE `website` DISABLE KEYS */;
INSERT INTO `website` VALUES (16,'_Bakla'),(29,'190'),(2,'Bloomsbury'),(14,'Crunchyroll'),(23,'Devsisters'),(25,'EA'),(4,'Good Reads'),(7,'Goodreads'),(3,'Harry Potter Wiki'),(22,'HoYoverse'),(13,'Hulu'),(10,'IMDb'),(5,'Jane Austen Society'),(27,'Minecraft'),(12,'Netflix'),(8,'Official Author Site'),(15,'Official Publisher (Futabasha)'),(6,'Project Gutenberg'),(26,'Riot Games'),(9,'Seven Seas Entertainment'),(28,'Steam'),(24,'Supercell'),(11,'Wikipedia'),(1,'Wizarding World');
/*!40000 ALTER TABLE `website` ENABLE KEYS */;
UNLOCK TABLES;

INSERT INTO  users values(34, 'moderator', '$argon2i$v=19$m=65536,t=10,p=1$119AujK0R6wDnNUQ5XVVSg$LIr1dJbDptlQxr2S9r4utFbyx3X/NwP0GCuhsY2nQr0', 'MODERATOR');

--
-- Final view structure for view `login_view`
--

CREATE OR REPLACE VIEW `login_view` AS
SELECT
    `users`.`username` AS `username`,
    `users`.`password` AS `password`,
    `users`.`role` AS `role`
FROM `users`;

--
-- Final view structure for view `media_category_overview`
--

CREATE OR REPLACE VIEW `media_category_overview` AS
SELECT
    `m`.`media_type` AS `media_type`,
    `c`.`name` AS `category_name`
FROM `media` `m`
JOIN `media_category`
    ON `m`.`media_id` = `media_category`.`media_id`
JOIN `category` `c`
    ON `media_category`.`category_id` = `c`.`category_id`;

-- Index Creation
CREATE INDEX `idx_media_name` ON `media` (`name`);
CREATE INDEX `idx_media_type` ON `media` (`media_type`);
CREATE INDEX `idx_media_year` ON `media` (`release_year`);
CREATE INDEX `idx_media_type_year` ON `media` (`media_type`, `release_year`);
CREATE INDEX `idx_person_name` ON `person` (`name`);
CREATE INDEX `idx_company_name` ON `company` (`name`);
CREATE INDEX `idx_platform_name` ON `platform` (`name`);

-- Setup Permissions for Moderator
GRANT SELECT, INSERT, UPDATE ON `thebackroom_db`.* TO 'app_moderator'@'localhost';
GRANT DELETE ON `thebackroom_db`.`media_access` TO 'app_moderator'@'localhost';
GRANT DELETE ON `thebackroom_db`.`media_category` TO 'app_moderator'@'localhost';
GRANT DELETE ON `thebackroom_db`.`media_company` TO 'app_moderator'@'localhost';
GRANT DELETE ON `thebackroom_db`.`media_game_mode` TO 'app_moderator'@'localhost';
GRANT DELETE ON `thebackroom_db`.`media_game_platform` TO 'app_moderator'@'localhost';
GRANT DELETE ON `thebackroom_db`.`media_personnel` TO 'app_moderator'@'localhost';

-- Setup Permissions for Normal User
GRANT SELECT ON `thebackroom_db`.* TO 'app_user'@'localhost';
GRANT INSERT ON `thebackroom_db`.`users` TO 'app_user'@'localhost';

FLUSH PRIVILEGES;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-04-27 21:59:10
