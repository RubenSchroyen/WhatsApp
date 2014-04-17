package worms.model;

import worms.util.Util;

public class Projectile 
{	
	World world;
	private double y;
	private double x;
	private double radius;
	final double density = 7800;
	private double mass;
	private double force;
	Worm currentWorm = world.currentWorm();
	Worm worm;
	private final double g = 9.80665;
	private double time;
	private double distance;
	private double velocity;
	
	public Projectile(double x, double y) 
	{
		this.setPosX(x);
		this.setPosY(y);
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
		return x;
	}

	public double getRadius() 
	{
		return radius;
	}

	public boolean isActive() 
	{
		if (world.projectileInBounds(this) && world.isPassable(this.getPosX(), this.getPosY(), this.getRadius()))
			return true;
		if (worm.getPosX() == this.getPosX() || worm.getPosY() == this.getPosY())
		{
			if (currentWorm.getSelectedWeapon() == "Bazooka")
				worm.setHP(worm.getHP() - 80);
			if (currentWorm.getSelectedWeapon() == "Rifle")
				worm.setHP(worm.getHP() - 20);
			return false;
		}
		return false;
	}

	public void destroy()
	{
		if (!isActive())
			world.removeProjectile(this);
	}
	
	public void shootRifle() 
	{
		Projectile projectile = new Projectile(currentWorm.getPosX() + 0.1, currentWorm.getPosY() + 0.1);
		projectile.setMass(10);
		projectile.setForce(1.5);
		Jump();
	}

	private void setMass(double mass) 
	{
		this.mass = mass;
	}
	
	private double getMass()
	{
		return mass;
	}

	public void shootBazooka(double propulsionYield) 
	{
		Projectile projectile = new Projectile(this.getRadius(), this.getRadius());
		projectile.setMass(300);
		projectile.setForce(2.5 + 0.07*propulsionYield);
		Jump();
	}

	private void setForce(double force) 
	{
		this.force = force;	
	}
	
	private double getForce()
	{
		return force;
	}
	
	/**
     * The method to make the worm jump to a new position
     * 
     * @post
     * 		We use the values of JumpStep and JumpTime to calculate the value of the new position the worm will be in and we drain all his AP
     * 		| new.getPosX() == newPosition[0]
     * 		| new.getPosY() == newPosition[1]
     */
    public void Jump() 
    {       
    	while (world.isPassable(this.getPosX(), this.getPosY(), this.getRadius()))
    	{
            double [] newPosition = this.JumpStep(this.JumpTime(this.getTime()));
            this.setPosX(newPosition[0]);
            this.setPosY(newPosition[1]);
    	}
    }
	   
	              
	/**
	 * The method to calculate the time the worm is in the air
	 *                     
	 * @return 	
	 * 		- this.getTime() if the worm has enough AP
	 * 			| !this.getCurrentAP == 0
	 * 		- 0.0 if there is not enough AP left
	 * 			| this.getCurrentAP == 0
	 * 
	 * @post
	 * 		If the worm still has AP, we set the force, velocity, density and time to their calculated values
	 * 		| new.getForce() == (5*this.getCurrentAP() + this.getMass() * g)
	 * 		| new.getVelocity() == (this.getForce() / this.getMass() * 0.5)
	 * 		| new.getDistance() == ((this.getVelocity² * sin(2*this.getAngle()) / g)
	 * 		| new.getTime() == (this.getDistance() / (this.getVelocity() * cos(this.getAngle()) )
	 * 
	 * 		If the worm does not have enough AP, we set the amount of time in the air to zero
	 * 		| new.getTime() == 0.0
	 */
    public double JumpTime(double time)
    {
	    this.setVelocity(this.getForce()/this.getMass()*0.5);
	    this.setDistance( (Math.pow(this.getVelocity(), 2) * Math.sin(2*currentWorm.getAngle()) ) / g);
	    time = this.getDistance() / (this.getVelocity() * Math.cos(currentWorm.getAngle())) ;
	    this.setTime(time);
	    return this.getTime();
	}
	   
	    
    private void setTime(double time) 
    {
		this.time = time;
	}

	private void setDistance(double distance) 
    {
		this.distance = distance;
	}

	private double getDistance() 
    {
		return distance;
	}

	private double getTime() 
    {
		return time;
	}

	
	private void setVelocity(double velocity) 
	{
		this.velocity = velocity;
	}   
	    
	/**
	 * Method to calculate the position of the worm while jumping
	 *    
	 * @param DeltaT
	 * 		The amount of time that has passed between two points in the jump
	 * 
	 * @return 
	 * 		- the array jumpstep with the values for x and y if the angle is between 0 and PI
	 * 			| !this.getAngle() < 0
	 * 			| !this.getAngle() > Math.PI
	 * 		- the array with the value of the original posX and posY (worm will not jump)
	 * 			| this.getAngle() < 0
	 * 			| this.getAngle() > Math.PI
	 * 
	 * @post
	 * 		The worm will get a new position every moment inside the jump. This position is calculated by giving the parameters velocityX
	 * 		and velocityY. These are used to calculate the position x and y.
	 * 			- If the angle is between 0 and PI
	 * 				| new.JumpStep == jumpstep
	 * 			- If the angle is not between 0 and PI
	 * 				| new.JumpStep == original
	 * 			
	 */
    public double[] JumpStep(double DeltaT)
    {               		
	        double velocityX = this.getVelocity() * Math.cos(currentWorm.getAngle());
	        double velocityY = this.getVelocity() * Math.sin(currentWorm.getAngle());
	        double x = this.getPosX() + (velocityX * DeltaT);
	        double y = this.getPosY() + (velocityY * DeltaT - 0.5*g*Math.pow(DeltaT, 2));
	        double jumpstep[] = new double[] {x,y};
	        
	        if (Util.fuzzyLessThanOrEqualTo(0, currentWorm.getAngle()) && (Util.fuzzyLessThanOrEqualTo(currentWorm.getAngle(), Math.PI)))
	        	return jumpstep;
	        
	        else 
	        {
	        	double[] original = new double[] {this.getPosX(),this.getPosY()};
	        	return original;
	        }
	    
	    }

	private double getVelocity() 
	{
		return velocity;
	}

	public void setRadius(double radius) 
	{
		this.radius = worm.getRadius() + 0.1;
	}

	public World getWorld() 
	{
		return world;
	}

}
