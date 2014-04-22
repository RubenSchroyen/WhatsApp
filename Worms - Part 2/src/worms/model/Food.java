package worms.model;



public class Food 
{
	World world;
	double y;
	double x;
	double radius;
	public boolean eaten = false;
	
	public Food(World world, double x, double y) 
	{
		this.setPosX(x);
		this.setPosY(y);
		this.setWorld(world);
	}

	public void setWorld(World world) 
	{
		this.world = world;
	}

	public void setPosY(double y) 
	{
		if (isValidPosition(this.getPosX(), y))
				this.y = y;
	}

	public void setPosX(double x) 
	{
		if (isValidPosition(x,this.getPosY()))
			this.x = x;
	}

	public double getPosY() 
	{
		return y;
	}

	public double getPosX() 
	{
		return x;
	}

	public World getWorld() 
	{
		return world;
	}

	public double getRadius() 
	{
		return 0.20;
	}
	
	public void setRadius(double radius)
	{
		this.radius = 0.20;
	}

	public boolean isActive() 
	{
		if (eaten)
			return false;
		return true;
	}
	
	public boolean isValidPosition(double posX, double posY) throws IllegalArgumentException 
	{
	        if ((posX == Double.NEGATIVE_INFINITY) || (posY == Double.NEGATIVE_INFINITY) || (posX == Double.POSITIVE_INFINITY) || (posY == Double.POSITIVE_INFINITY))
	        	throw new IllegalArgumentException("Not a valid value for position");
	        return true;
	}
	
	public void destroy()
	{
		if (!isActive())
		{
			world.removeFood(this);
			this.setWorld(null);
		}
	}

}
