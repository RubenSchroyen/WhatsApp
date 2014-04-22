package worms.model;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class World 
{
	private double worldHeight;
	private double worldWidth;
	private boolean[][] passableMap;
	public List<Worm> worms = new ArrayList<Worm>();
	public List<Food> fodder = new ArrayList<Food>();
	public List<Projectile> projectiles = new ArrayList<Projectile>();
	public List<Team> teams = new ArrayList<Team>();
	private int index = 0;
	private Team currentTeam;
	Random RandomGenerator = new Random();

	
	public World(double worldWidth, double worldHeight, boolean[][] passableMap, Random random) 
	{
		this.worms = new ArrayList<Worm>();
		this.fodder = new ArrayList<Food>();
		this.projectiles = new ArrayList<Projectile>();
		this.teams = new ArrayList<Team>();
		this.worldHeight = worldHeight;
		this.worldWidth = worldWidth;
		this.setPassableMap(passableMap);
		RandomGenerator = random;
	}
	
	public Worm currentWorm()
	{
		Worm worm = worms.get(this.getIndex());
		if (worm.getCurrentAP() == 0 && worms.size() > 1)
			this.nextWorm();
		return worm;
	}
	
	public void nextWorm()
	{
		this.setIndex(this.getIndex() + 1);
	}
	
	public int getIndex()
	{
		return index;
	}
	
	public void setIndex(int index)
	{
		if (index > worms.size() - 1)
			this.index = 0;
		else
			this.index = index;
	}
	
	public List<Worm> getWorms()
	{
		return worms;
	}
	
	public List<Food> getFodder()
	{
		return fodder;
	}
	
	public List<Projectile> getProjectiles()
	{
		return projectiles;
	}
	
	public List<Team> getTeams()
	{
		return teams;
	}
	
	public int amountOfWorms()
	{
		return worms.size();
	}
	
	public int amountOfTeams()
	{
		return teams.size();
	}
	
	public int amountOfProjectiles()
	{
		return projectiles.size();
	}
	
	public int amountOfFood()
	{
		return fodder.size();
	}
	
	public String getWinner()
	{
		if (isFinished() && this.amountOfTeams() == 1)
			return (teams.get(0).getName());
		if (isFinished() && this.amountOfTeams() == 0 && worms.size() == 1)
			return (worms.get(0).getName());
		return null;
	}

	public boolean isFinished()
	{
		if (teams.size() == 1 && this.amountOfWorms() == teams.get(0).getAllWorms().size())
			return true;
		if (teams.size() == 0 && worms.size() == 1)
			return true;
		return false;
	}
	
	public boolean wormExists(Worm worm)
	{
		return (worm != null);
	}

	public boolean wormInWorld(Worm worm)
	{
		return worms.contains(worm);
	}
	
	public void addWorm() 
	{
		
		double randomPositionX = RandomGenerator.nextDouble() * (this.getWorldWidth() + 1);
		double randomPositionY = RandomGenerator.nextDouble() * (this.getWorldHeight() + 1);
		double randomAngle = RandomGenerator.nextDouble() * 2 * Math.PI;
		double randomRadius = RandomGenerator.nextDouble() * 0.75 + 0.25;
		
		Worm worm = new Worm(this, randomPositionX, randomPositionY, randomRadius, randomAngle, "Eric");
		worm.setPosX(this.findAdjacentX(worm, randomPositionX));
		worm.setPosY(this.findAdjacentY(worm, randomPositionY));
		
		assert(wormExists(worm) && worm.getWorld() == this);
		assert(!wormInWorld(worm));
		
		if (wormInBounds(worm) && isAdjacent(worm.getPosX(), worm.getPosY(), worm.getRadius()))
		{
			if (fodder.size() > 1)
			{
				for ( Food food : fodder ) 
				{
					if (World.isOverlapping(worm.getPosX(), worm.getPosY(), worm.getRadius(), food.getPosX(), food.getPosY(), food.getRadius())) 
						return;
				}
			}
			
			if (worms.size() > 1)
			{
				for ( Worm otherWorm : worms ) 
				{
					if (World.isOverlapping(worm.getPosX(), worm.getPosY(), worm.getRadius(), otherWorm.getPosX(), otherWorm.getPosY(), otherWorm.getRadius())) 
						return;
				}
			}
			worms.add(worm);
			worm.addToTeam(this.getCurrentTeam());
		}
		else 
			addWorm();
	}
	
	public Team getCurrentTeam() 
	{
		return currentTeam;
	}

	public void setCurrentTeam(Team currentTeam) 
	{
		this.currentTeam = currentTeam;
	}

	public double findAdjacentX(Worm worm, double x)
	{
		double center = this.getWorldWidth() / 2;
		double currentX = worm.getPosX();
		
		while (Math.abs(center - currentX) > worm.getRadius() && !isAdjacent(currentX, worm.getPosY(), worm.getRadius())) 
		{
			if (currentX < center) 
			{
				System.out.println("1a");
				currentX += 1 * (this.getWorldWidth() / passableMap[0].length);
			}
			
			if (currentX > center) 
			{
				System.out.println("1b");
				currentX -= 1 * (this.getWorldWidth() / passableMap[0].length);
			}
			System.out.println("1c");
			worm.setPosX(currentX);
		}
		System.out.println("1d");
		if (Math.abs(center - currentX) > worm.getRadius())
			return currentX;
		else
			return 0;
	}
	
	public double findAdjacentY(Worm worm, double y)
	{
		double center = this.getWorldHeight() / 2;
		double currentY = worm.getPosY();
		while (Math.abs(center - currentY) > worm.getRadius() && !isAdjacent(worm.getPosX(), currentY, worm.getRadius())) 
		{	
			if (currentY < center) 
			{
				currentY += 1 * (this.getWorldHeight() / passableMap.length);
			}
			
			if (currentY > center) 
			{
				currentY -= 1 * (this.getWorldHeight() / passableMap.length);
			}
			worm.setPosY(currentY);
		}
		if (Math.abs(center - currentY) > worm.getRadius())
			return currentY;
		else
			return 0;
	}
	
	public double findAdjacentX(Food food, double x)
	{
		double center = this.getWorldWidth() / 2;
		double currentX = food.getPosX();
		
		while (Math.abs(center - currentX) > food.getRadius() && !isAdjacent(currentX, food.getPosY(), food.getRadius())) 
		{
			if (currentX < center) 
			{
				currentX += 1 * (this.getWorldWidth() / passableMap[0].length);
				
			}
			
			if (currentX > center) 
			{
				currentX -= 1 * (this.getWorldWidth() / passableMap[0].length);
				
			}
			food.setPosX(currentX);
			
		}
		if (Math.abs(center - currentX) > food.getRadius())
			return currentX;
		else
			return 0;
	}
	
	public double findAdjacentY(Food food, double y)
	{
		double center = this.getWorldHeight() / 2;
		double currentY = food.getPosY();
		while (Math.abs(center - currentY) > food.getRadius() && !isAdjacent(food.getPosX(), currentY, food.getRadius())) 
		{	
			if (currentY < center) 
			{
				currentY += 1 * (this.getWorldHeight() / passableMap.length);
			}
			
			if (currentY > center) 
			{
				currentY -= 1 * (this.getWorldHeight() / passableMap.length);
			}
			food.setPosY(currentY);
		}
		if (Math.abs(center - currentY) > food.getRadius())
			return currentY;
		else
			return 0;
	}
	
	public void removeWorm(Worm worm) throws IllegalArgumentException
	{
		assert (wormExists(worm)) && (worm.getWorld() != null);
		assert (wormInWorld(worm));
		worms.remove(worm);
	}
	
	public boolean foodExists(Food food)
	{
		return (food != null);
	}

	public boolean foodInWorld(Food food)
	{
		return fodder.contains(food);
	}
	
	public void addFood() 
	{
		double randomPositionX = RandomGenerator.nextDouble() * (this.getWorldWidth() + 1);
		double randomPositionY = RandomGenerator.nextDouble() * (this.getWorldHeight() + 1);
		
		Food food = new Food(this, randomPositionX, randomPositionY);
	
		food.setPosX(this.findAdjacentX(food, randomPositionX));
		food.setPosY(this.findAdjacentY(food, randomPositionY));
		assert(foodExists(food) && food.getWorld() == this);
		assert(!foodInWorld(food));
		if (foodInBounds(food) && isAdjacent(food.getPosX(), food.getPosY(), food.getRadius()))
		{
			if (fodder.size() > 1)
			{
			
				for ( Food otherFood : fodder ) 
				{
					if (World.isOverlapping(food.getPosX(), food.getPosY(), food.getRadius(), otherFood.getPosX(), otherFood.getPosY(), otherFood.getRadius())) 
						return;
				}
			}
			
			if (worms.size() > 1)
			{
				for ( Worm otherWorm : worms ) 
				{
					if (World.isOverlapping(food.getPosX(), food.getPosY(), food.getRadius(), otherWorm.getPosX(), otherWorm.getPosY(), otherWorm.getRadius())) 
						return;
				}
			}
			fodder.add(food);
		}
		else 
			addFood();
	}
	
	public void removeFood(Food food) throws IllegalArgumentException
	{
		assert (foodExists(food)) && (food.getWorld() == this);
		assert (foodInWorld(food));
		fodder.remove(food);
	}
	
	public boolean projectileExists(Projectile projectile)
	{
		return (projectile != null);
	}

	public boolean projectileInWorld(Projectile projectile)
	{
		return projectiles.contains(projectile);
	}
	
	public void addProjectile(Projectile projectile) throws IllegalArgumentException
	{
		assert (projectileExists(projectile)) && (projectile.getWorld() == this);
		assert (!projectileInWorld(projectile));
		projectiles.add(projectile);
	}
	
	public void removeProjectile(Projectile projectile) throws IllegalArgumentException
	{
		assert (projectileExists(projectile)) && (projectile.getWorld() != null);
		assert (projectileInWorld(projectile));
		projectiles.remove(projectile);
	}
	
	public boolean teamExists(Team team)
	{
		return (team != null);
	}

	public boolean teamInWorld(Team team)
	{
		return teams.contains(team);
	}
	
	public void addTeam(Team team) throws IllegalArgumentException
	{
		assert (teamExists(team)) && (team.getWorld() == null);
		assert (!teamInWorld(team));
		assert (teams.size() < 10);
		this.setCurrentTeam(team);
		teams.add(team);
	}
	
	public void removeTeam(Team team) throws IllegalArgumentException
	{
		assert (teamExists(team)) && (team.getWorld() != null);
		assert (teamInWorld(team));
		teams.remove(team);
	}
	

	
	public boolean wormInBounds(Worm worm)
	{
		if (worm.getPosX() < 0 || worm.getPosX() > this.getWorldWidth() || worm.getPosY() < 0 || worm.getPosY() > this.getWorldHeight())
			return false;
		else 
			return true;
	}
	
	public boolean foodInBounds(Food food)
	{
		if (food.getPosX() < 0 || food.getPosX() > this.getWorldWidth() || food.getPosY() < 0 || food.getPosY() > this.getWorldHeight())
			return false;
		else 
			return true;
	}
	
	public boolean projectileInBounds(Projectile projectile)
	{
		if (projectile.getPosX() < 0 || projectile.getPosX() > this.getWorldWidth() || projectile.getPosY() < 0 || projectile.getPosY() > this.getWorldHeight())
			return false;
		else 
			return true;
	}
	
	public double getWorldHeight() 
	{
		return worldHeight;
	}
	
	public void setWorldHeight(double worldHeight) 
	{
		if (isValidDimensions(this.getWorldWidth(), worldHeight))
			this.worldHeight = worldHeight;
	}
	
	public double getWorldWidth() 
	{
		return worldWidth;
	}
	
	public void setWorldWidth(double worldWidth) 
	{
		if (isValidDimensions(worldWidth, this.getWorldHeight()))
			this.worldWidth = worldWidth;
	}

	public boolean[][] getPassableMap() 
	{
		return passableMap;
	}

	private void setPassableMap(boolean[][] map) throws IllegalArgumentException{
		if (map.length == 0)
			throw new IllegalArgumentException("Empty map!");
		else if (map[0].length == 0)
			throw new IllegalArgumentException("Empty map!");
		this.passableMap = map;
	}
	
	public boolean isValidDimensions(double width, double height) throws IllegalArgumentException
	{
		if (width < 0 || width > Double.MAX_VALUE )
			throw new IllegalArgumentException("Not a valid dimension for World");
		if (height < 0 || height > Double.MAX_VALUE)
			throw new IllegalArgumentException("Not a valid dimension for World");
		return true;
	}
	
	public int getPixelsX() 
	{
		return this.getPassableMap()[0].length;
	}

	public int getPixelsY() 
	{
		return this.getPassableMap().length;
	}

	public double getResolutionX() 
	{
		return (this.getWorldWidth() / ((double) this.getPixelsX()));
	}


	public double getResolutionY() 
	{
		return (this.getWorldHeight() / ((double) getPixelsY()));
	}
	
	private int[] positionToPixel(double x, double y) throws IllegalArgumentException 
	{
		if (!isWithinBoundaries(x,y))
			throw new IllegalArgumentException("Not within the boundaries of this world");
		int pixelX = (int) Math.round(x * ((double) this.getPixelsX()-1 ) / this.getWorldWidth());
		int pixelY = (this.getPixelsY()-1) - (int) Math.round(y * ((double) this.getPixelsY()  -1 ) / this.getWorldHeight());
		
		int[] pixelPosition = new int[2];
		pixelPosition[0] = pixelX;
		pixelPosition[1] = pixelY;
		
		return pixelPosition;
	}

	
	public boolean isWithinBoundaries(double x, double y) 
	{
		if (x < (double) 0 || x > this.getWorldWidth() || y < (double) 0 || y > this.getWorldHeight())
			return false;
		return true;
	}

	private boolean isPassablePixel(int pixelX, int pixelY) 
	{
		return this.getPassableMap()[pixelY][pixelX];
	}

	
	private boolean isPassablePosition(double x, double y) 
	{
		if (!isWithinBoundaries(x,y))
			return false;
		
		int[] pixel = positionToPixel(x,y);
		return isPassablePixel(pixel[0],pixel[1]);
	}

	public boolean isPassable(double x, double y, double radius) 
	{ 
		if (!isPassablePosition(x, y))
			return false;

		int amountOfPixelsX = (int) Math.ceil(0.1*radius / this.getResolutionX());
		int amountOfPixelsY = (int) Math.ceil(0.1*radius / this.getResolutionY());

		double testX = 0;
		double testY = 0;
		for (int pixelX = 0; pixelX < amountOfPixelsX; pixelX++) 
		{
			for (int pixelY = 0; pixelY < amountOfPixelsY; pixelY++) 
			{
				testX = pixelX * this.getResolutionX();
				testY = pixelY * this.getResolutionY();
				if (Math.sqrt(Math.pow(testX,2) + Math.pow(testY,2)) <= 0.1*radius) 
				{
					if (!isPassablePosition(x + testX, y + testY))
						return false;
					if (!isPassablePosition(x - testX, y + testY))
						return false;
					if (!isPassablePosition(x + testX, y - testY))
						return false;
					if (!isPassablePosition(x - testX, y - testY))
						return false;
				}
			}
		}
		return true;
	}

	public boolean isAdjacent(double x, double y, double radius) 
	{
		if (!isPassable(x,y,radius))
			return false;
		
		for (double testAngle = 0; testAngle < 2*Math.PI; testAngle += 2*Math.PI/40) 
		{
			double deltaX = (0.1*radius + this.getResolutionX()) * Math.cos(testAngle);
			double deltaY = (0.1*radius + this.getResolutionY()) * Math.sin(testAngle);

			if (isWithinBoundaries(x + deltaX, y + deltaY) && !isPassablePosition(x + deltaX, y + deltaY)) 
				return true;
		}
		return false;
	}
	
	
	public static boolean isOverlapping(double X, double Y, double radius, double x, double y, double Radius) 
	{
		double deltaX = x - X;
		double deltaY = y - Y;

		double norm = Math.sqrt(Math.pow(deltaX,2) + Math.pow(deltaY,2));
		double sumRadius = Radius + radius;

		return (norm < sumRadius);
	}
}
