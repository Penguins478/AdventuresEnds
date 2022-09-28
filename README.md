# README #

**Name:**	Ryan Kwong

**Period:**	4

**Game Title:** Adventure's Ends

## Game Proposal ##

In this game, I want to create a fun single player role-playing game where the user controls a player that must 
conquer multiple dungeon rooms. The player is equipped with only a small gun, however, killing monsters will sometimes drop power-ups that will make the player stronger. Additionally, dungeon monsters consists of gunners and kamikazes. The goal of the game is to defeat the final boss
in the last corridor of the dungeon. However, along the way, there will be several monsters to kill, traps to avoid, 
and a trick the player must realize to have a chance to fight the various bosses.

Game Controls:

+ Arrow key controls for directional movement
	+ Down key is used to enter a hole
	+ Up key is used to pick up a power-up
+ W and S keys for aiming the gun
	+ W key is aiming upwards
	+ S key is aiming downwards
+ Space bar for shooting bullets
+ Shift key for activating the player's special ability (rapid fire)

Game Elements:

+ Game displays a top down view of the dungeon and the player
+ Bullets fired by the player travel in a straight line until it hits an object
	+ Bullets fired by the player do not ricochet off walls
	+ Projectiles fired by monsters ricochet off walls once
	+ Bullets can hit more than one object at a time
+ All monsters move towards the player
	+ Each monster type has different speeds
	+ Deeper dungeon rooms contain faster monsters
+ Players can only be hit 3 times before they die
+ Players are knocked back if they take damage while moving
+ Monsters have only one hitpoint
	+ Bullets must be fired at the monster to deal damage to them
		+ Bullets deal 1 damage to monsters
	+ Traps do not harm monsters
+ Gunners and kamikazes (melee and ranged attackers) can drop power-ups
	+ 5 percent chance to drop a health restore
		+ Players cannot have over 3 health
	+ 5 percent chance to drop a speed increase
		+ Players have a speed maximum
+ Monsters may have melee or ranged attacks
	+ All hits from monsters will deal 1 damage
+ Collision between a player and a monster results in the player and monster losing 1 hitpoint
+ Collision between a player and a boss results in the player instantly dying
+ Traps only include acid pools
+ 8 corridors within the dungeon
	+ Rooms 1 – 6 contain one more than their room level amount of monsters 
	+ Room 7 consists of 3 ranged and melee monsters with a mysterious sound
	+ Room 8 has two parts
		+ First part is the fight between the player and the robot boss
		+ Second part is the fight between the player and the experiment boss
+ Robot boss fires up to 10 homing bullets
+ Experiment boss fires many bullets and leaves acid traps on where it has stood
+ All monsters are killed before moving to the next corridor
+ The hole needed to proceed to the next corridor is placed randomly inside the rooms
	+ The hole will not be on a trap
+ Player have unlimited bullets

How to Win:

+ Defeat the final boss with at least one hitpoint remaining

## Link Examples ##
Provide links to examples of your game idea.  This can be a playable online game, screenshots, YouTube videos of gameplay, etc.

+ [Soul Knight](https://www.youtube.com/watch?v=fIMcEStojeY) - My inspiration.

## Teacher Response ##

**Approved**

Good game idea that is appropriate for our Actor / World system and it sounds like you've spent careful time thinking about which
features you want to implement.  One thing to note is how levels are created.  If it's just random everything with enemies simply
getting faster over time, that lead to good gameplay.  Your game will need a good flow in terms of level completion and exactly
how the game progresses over time.

## Class Design and Brainstorm ##

I think adding an aiming control for the player would make the game more
challenging. Also, adding a health restore and speed increase would make 
the game a lot more enjoyable to play. Adding background music and shooting sounds
would make the game more immersive too.

To make the bullet move in the appropriate direction, I will use the player's gun to create trigonometric ratios. The gun's width will be considered the hypotenuse, because it is the imaginary triangle's longest side. Furthermore, I do have a given angle, as the ImageView class has a built in getRotate() method. Using the hypotenuse and the given angle, I can use cosine and sine operations to find the correct point and trajectory that bullet should have.

The game creates the stage and scene in which the world and all the actors can exist in. Moreover, it initializes the world to a title state that the user can interact with. The title state, info state, game state, etc. are all subclasses of State. The superclass State allows a playing field for actors to perform at. Finally, guns, characters, bullets, enemies, etc. are all subclasses of actors that can perform actions after every AnimationTimer tick. All of these elements are bound together in a World. Specifically, they are in the AdventuresEnds world. Actors and States can access the World, because the World is their over-arching parent.

A considerable amount of objects in this game explode on Impact, so I added an Impact class
to make the code less repetitive and more organized.

## Development Journal ##

Every day you work, keep track of it here.

** May 24, 2020 (4 hours)**

Goal:  Get the foundation of the game started

Work accomplished:  Work was smooth today, as there were no huge road
					blocks. I currently have 2 main scenes where the user
					can select the instructions or just simply start the 						game. In addition, the instructions screen also has a
					button that leads directly to the game when the user
					is done reading about how to play the game. My game 							currently has mechanics in it. It only has a few images 						that I placed for testing purposes. However, I made the 						images move slightly to make them seem more lively.
					 

** May 25, 2020 (2 hours) **

Goal:  Get primary Player mechanics finished

Work accomplished:  Today, I gave my Player the ability to shoot bullets. 						Additionally, the user can aim the gun by tilting the 						gun which is controlled by the W and S keys for up and 						down, respectively. The Player can then shoot bullets at 					an interval (the reload time). Finally, Player movement 						is controlled by the arrow keys. Aside from the Player, 						I also added the Player avatar to the title screen to 						make the start page less empty. Overall, this session of 					adding mechanics to the Player was a little challenging.

** May 27, 2020 (4 hours) **

Goal: Add more features to the Player and create enemies

Work accomplished: Over the course of two days, I managed to add bounds 						   bounds detection for the player, making the Player unable 				   to walk outside the Walls. In addition, I added a health 					   bar visual for the user in the top left corner of the game 
				   state. Once the bar is empty, the Player fades away and 					   dies, resulting in the game being over. Moreover, after 					   improving the Player, I also added Enemies. Currently, the 
				   enemies only consists of Bombers that follow the Player 
				   and explode on impact, damaging the Player. However, I 					   also added a feature where the bomb can knock back the 					   Player. Finally, I added a game over screen that is shown 				   when the player's health drops to 0. Hopefully, in my next 				   session of coding this game, their will be a Gunner enemy 				   and multiple levels.
				   
** May 29, 2020 (2 hours) **

Goal: Create a Gunner that fires bullets that damage the Player

Work accomplished: In today's session, I created a Gunner that shoots red bullets in a 20 					  degree firing cone. This Gunner keeps track of the Player position,  					  rotating to the appropriate region that the Player is in. Furthermore, 					  these Gunner's projectiles can ricochet off walls once, making these 					  Gunners very deadly to Players, as too many of them will make dodging 					  bullets very difficult. Now I have two types of minions that can kill the 				  Player. Next, I will generate rooms containing multiple of these minions 
				  that the Player will have to eliminate.
				  
** May 30, 2020 (6 hours) **

Goal: Create multiple dungeon rooms and the two bosses the Player will encounter

Work accomplished: Today, I created all 8 rooms within the dungeon along with the two bosses 
				  in the final room and added a few sound effects. After playing the game for 30 minutes, I realized that 				  too many times the Player is instantly killed from the Gunner fast gun 					  shots. Thus, I made specifically the Gunners a wait time before they can 				  shoot any bullets. In addition, I increased the Player's firing cone from 
				  20 degrees to 45 degrees, allowing for more range. Finally, I gave the 
				  Player the ability to rapid fire gun shots every 5 seconds. This makes
				  the boss fights more manageable. Ultimately, my game at this point is close
				  to being finished. However, there are still many areas that need polishing.

** May 31, 2020 (8 hours) **

Goal: Fix collision bug and add power-ups

Work accomplished: For the last day of this project, I fixed a collision bug where bullets 
				  sometimes pass through objects without impacting. Additionally, I added two
				  power-ups including a speed up ability and a health restore. Along with 					  these two major goals completed, I polished the game by adding enemies to 				  the world 3 at a time to prevent overcrowding. Finally, I updated my 					  instructions to account for all these changes.

***
***