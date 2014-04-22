package worms.model;


import java.util.Collection;
import java.util.Random;


public class Facade implements IFacade 
{
 
		private Random random = new Random();
		private Team team;

		/**
		 * This method will create a worm and immediately give him his needed features:
		 * 
		 * @param x
		 * 		The X-position of a worm in meters
		 * 
		 * @param y
		 * 		The Y-position of a worm in meters
		 * 
		 * @param direction
		 * 		The direction a worm is facing
		 * 
		 * @param radius
		 * 		The radius of a worm in meters
		 * 
		 * @param name
		 * 		The name of a worm
		 * 
		 * @return worm   (a worm with these features)
		 * 		
		 */
        @Override
        public Worm createWorm(World world, double x, double y, double direction, double radius, String name) 
        {
                Worm worm = new Worm(world, x , y, radius, direction, "Default");
                worm.setHP(worm.getMaxHP());
                worm.setCurrentAP(worm.getMaxAP());
                return worm;
        }
        
        
        /**
         * This method checks if the worm can do a certain turn
         * 
         * @param worm
         * 		The newly created worm
         * 
         * @param angle
         * 		The angle of direction a worm is facing
         * 
         * @return worm.inspectTurn(angle) (returns false if the current amount of AP is not high enough)
         * 
         */
        @Override
        public boolean canTurn(Worm worm, double angle) 
        {
                return worm.isValidTurn(angle);
        }
        
        /**
         * This method turns the worm by a certain angle
         *
         * @param worm
         * 		The newly created worm
         * 
         * @param angle
         * 		The angle of direction a worm is facing
         * 
         * @effect	
         * 		The worm's angle will change to the new one if there is enough AP left
         * 
         * @throws ModelException 
         *		If the turn is not a valid one (not enough AP)
         *		| !worm.isValidTurn()
         */
        @Override
        public void turn(Worm worm, double angle) throws ModelException
        {
        	try
			{
				worm.Turn(angle);
			}
			catch (IllegalArgumentException exc)
        	{
             throw new ModelException(exc.getMessage());
            } 
        }
        
       
        /**
         * This method recalls the value of JumpStep in the class worms (the X- and Y-position of the worm at all times in the air while jumping)
         * 
         * @param worm
         * 		The newly created worm
         * 
         * @param t
         * 		The time the worm is in the air while jumping
         * 
         * @return worm.JumpStep(t)  (The position for X and Y of the worm at all times in the jump)
         */
        @Override
        public double[] getJumpStep(Worm worm, double t) 
        {
                return worm.JumpStep(t);
        }
        
        /**
         * This method recalls the value of the X-position of the worm in class worm
         * 
         * @param worm
         * 		The newly created worm
         * 
         * @return worm.getPos_x()   (The X-position of the worm in class worm)
         * 	
         */
        @Override
        public double getX(Worm worm) 
        {
                return worm.getPosX();
        }
        
        /**
         * This method recalls the value of the Y-position of the worm in class worm
         * 
         * @param worm
         * 		The newly created worm
         * 
         * @return worm.getPos_y()   (The Y-position of the worm in class worm)
         * 	
         */
        @Override
        public double getY(Worm worm) 
        {
                return worm.getPosY();
        }
        
        /**
         * This method recalls the value of the angle of direction of the worm in class worm
         * 
         * @param worm
         * 		The newly created worm
         * 
         * @return worm.getAngle()   (The angle of direction of the worm in class worm)
         * 	
         */
        @Override
        public double getOrientation(Worm worm) 
        {
                return worm.getAngle();
        }
        
        /**
         * This method recalls the value of the radius of the worm in class worm
         * 
         * @param worm
         * 		The newly created worm
         * 
         * @return worm.getRadius()   (The radius of the worm in class worm)
         * 	
         */
        @Override
        public double getRadius(Worm worm) 
        {
                return worm.getRadius();
        }
        
        /**
         * This method sets the value of the radius of the worm
         * 
         * @param worm
         * 		The newly created worm
         * 
         * @param newRadius
         * 		The new radius the worm gets
         *
         * @throws ModelException 
         * 		If the radius of the worm is smaller than the minimal radius of 0.25 meters
         * 		| worm.getRadius() < this.getMinimalRadius(worm)
         * 
         * @effect
         * 		The radius of the worm gets changed to its new radius if it is bigger than 0.25 meters
         * 	
         */
        @Override
        public void setRadius(Worm worm, double newRadius) throws ModelException
        {
        	 if (worm.getRadius() < this.getMinimalRadius(worm)) 
        		 throw new ModelException("Your radius is too small");
             worm.setRadius(newRadius);  
        }
 
        /**
         * This method recalls the value of the minimal radius of the worm in class worm
         * 
         * @param worm
         * 		The newly created worm
         * 
         * @return worm.getmin_Radius()   (The minimal radius of the worm in class worm)
         * 	
         */
        @Override
        public double getMinimalRadius(Worm worm)
        {
                return worm.minRadius; 
        }
 
        /**
         * This method recalls the value of the current amount of action points of the worm in class worm
         * 
         * @param worm
         * 		The newly created worm
         * 
         * @return worm.getcurrent_AP()   (The current amount of action points of the worm in class worm)
         * 	
         */
        @Override
        public int getActionPoints(Worm worm) 
        {
                return worm.getCurrentAP();
        }
 
        /**
         * This method recalls the value of the maximum amount of action points of the worm in class worm
         * 
         * @param worm
         * 		The newly created worm
         * 
         * @return worm.getMax_AP()   (The maximum amount of action points of the worm in class worm)
         * 	
         */
        @Override
        public int getMaxActionPoints(Worm worm) 
        {
                return worm.getMaxAP();
        }
 
        /**
         * This method recalls the name of the worm in class worm
         * 
         * @param worm
         * 		The newly created worm
         * 
         * @return worm.getName()   (The name of the worm in class worm)
         * 	
         */
        @Override
        public String getName(Worm worm) 
        {
                return worm.getName();
        }
 
        /**
         * This method renames the newly created worm to a chosen name
         * 
         * @param worm
         * 		The newly created worm
         * 
         * @param newName
         * 		The newly chosen name for the worm
         * 
         * @try worm.setName(newName) (We try to rename the worm)
         * 
         * @throws ModelException
         * 		If worm.setName(newName) throws IllegalArgumentException
         */
        @Override
        public void rename(Worm worm, String newName) 
        {
        	
        	try 
        	{
        		worm.setName(newName);
        	}
            catch (IllegalArgumentException exc)
            	{
                 throw new ModelException(exc.getMessage());
                }
        }
 
        /**
         * This method recalls the mass of the worm in class worm
         * 
         * @param worm
         * 		The newly created worm
         * 
         * @return worm.getMass()   (The mass of the worm in class worm)
         * 	
         */
        @Override
        public double getMass(Worm worm) 
        {
                return worm.getMass();
        }

    
        
        
 /*********************************************************************************************
  * 																						  *
  * 									PART 2 ADDITIONS									  *
  * 																						  *       
  *********************************************************************************************/
       

		@Override
		public Collection<Worm> getWorms(World world) 
		{
			return world.getWorms();
		}

		@Override
		public String getWinner(World world) 
		{
			return world.getWinner();
		}

		@Override
		public Collection<Food> getFood(World world) 
		{
			return world.getFodder();
		}

		@Override
		public double getX(Food food) 
		{
			return food.getPosX();
		}

		@Override
		public double getY(Food food) 
		{
			return food.getPosY();
		}

		@Override
		public double getRadius(Food food) 
		{
			return food.getRadius();
		}

		@Override
		public String getSelectedWeapon(Worm worm) 
		{
			return worm.getSelectedWeapon();
		}

		@Override
		public Worm getCurrentWorm(World world) 
		{
			return world.currentWorm();
		}

		@Override
		public void addEmptyTeam(World world, String newName) throws ModelException
		{
			if (world.amountOfTeams() < 10)
			{
				Team team = new Team(newName, world);
				world.addTeam(team);	
			}
			if (world.amountOfTeams() > 10)
			{
				throw new ModelException("Too many teams in this world");
			}
		}

		@Override
		public void addNewWorm(World world) 
		{
			world.addWorm();
		}

		@Override
		public void addNewFood(World world) 
		{
			world.addFood();
		}

		@Override
		public void startGame(World world) 
		{
			world = new World(world.getWorldHeight(), world.getWorldWidth(), world.getPassableMap(), random);
		}

		@Override
		public boolean isGameFinished(World world) 
		{
			if (world.amountOfTeams() == 1)
				return true;
			return false;
		}

		@Override
		public void startNextTurn(World world) 
		{
			world.nextWorm();
			world.currentWorm().setCurrentAP(world.currentWorm().getMaxAP());
			world.currentWorm().setHP(world.currentWorm().getHP() + 10);			
		}

		@Override
		public boolean isImpassable(World world, double randomizedX, double randomizedY, double testRadius) 
		{
			return world.isPassable(randomizedX, randomizedY, testRadius);
		}

		@Override
		public boolean isAdjacent(World world, double randomizedX, double randomizedY, double testRadius) 
		{
			return world.isAdjacent(randomizedX, randomizedY, testRadius);
		}

		@Override
		public double getJumpTime(Worm worm, double jumpTimeStep) 
		{
			return worm.JumpTime();
		}

		@Override
		public String getTeamName(Worm worm) 
		{
			if (worm.getTeam() != null)
				return team.getName();
			return null;
		}

		@Override
		public int getMaxHitPoints(Worm worm) 
		{
			return worm.getMaxHP();
		}

		@Override
		public int getHitPoints(Worm worm) 
		{
			return worm.getHP();
		}

		@Override
		public void jump(Worm worm, double jumpTimeStep) 
		{
			worm.Jump();
		}

		@Override
		public boolean isAlive(Worm worm) 
		{
			return worm.isAlive();
		}

		@Override
		public boolean canMove(Worm worm) 
		{
			return worm.canMove();
		}

		@Override
		public boolean canFall(Worm worm) 
		{
			return worm.canFall();
		}

		@Override
		public void fall(Worm worm) 
		{
			worm.fall();
		}

		@Override
		public void move(Worm worm) throws ModelException
		{
			try
			{
				worm.move();
			}
			catch (IllegalArgumentException exc)
        	{
             throw new ModelException(exc.getMessage());
            }
		}

		@Override
		public void selectNextWeapon(Worm worm) 
		{
			worm.selectNextWeapon();
			worm.setSelectedWeapon();
		}

		@Override
		public void shoot(Worm worm, int propulsionYield) 
		{
			if (canShoot(worm))
				worm.shoot(propulsionYield);
		}

		@Override
		public Projectile getActiveProjectile(World world) 
		{
			if (world.amountOfProjectiles() == 1)
				return world.getProjectiles().get(0);
			return null;
		}

		@Override
		public double getJumpTime(Projectile projectile, double jumpTimeStep) 
		{
			return projectile.JumpTime(jumpTimeStep);
		}

		@Override
		public double getX(Projectile projectile) 
		{
			return projectile.getPosX();
		}

		@Override
		public double getY(Projectile projectile) 
		{
			return projectile.getPosY();
		}

		@Override
		public double getRadius(Projectile projectile) 
		{
			return projectile.getRadius();
		}

		@Override
		public void jump(Projectile projectile, double jumpTimeStep) 
		{
			projectile.Jump(jumpTimeStep);
		}

		@Override
		public double[] getJumpStep(Projectile projectile, double elapsedTime) 
		{
			return projectile.JumpStep(elapsedTime);
		}

		@Override
		public boolean isActive(Food food) 
		{
			return food.isActive();
		}

		@Override
		public boolean isActive(Projectile projectile) 
		{
			return projectile.isActive();
		}
 
		
		public Food createFood(World world, double x, double y) 
		{
			Food food = new Food(world, x, y);
			return food;
		}
		
		public Projectile createProjectile(World world, double x, double y) 
		{
			Projectile projectile = new Projectile(world.currentWorm());
			return projectile;
		}
		
		public Team createTeam(World world, String name)
		{
			return new Team(name, world);
		}

		public World createWorld(double width, double height, boolean[][] passableMap, Random random) 
		{
			return new World(width, height, passableMap, random);
		}

		public boolean canShoot(Worm worm)
		{
			return worm.canShoot();
		}

}