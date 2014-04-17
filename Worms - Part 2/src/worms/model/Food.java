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
	}

	public void setWorld(World world) 
	{
		this.world = world;
	}

	public void setPosY(double y) 
	{
		this.y = y;
	}

	public void setPosX(double x) 
	{
		this.x = x;
	}

	public double getPosY() 
	{
		return y;
	}

	public double getPosX() 
	{
		return y;
	}

	public World getWorld() 
	{
		return world;
	}

	public double getRadius() 
	{
		return radius;
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
	
	public void destroy()
	{
		if (!isActive())
			world.removeFood(this);
	}

}
