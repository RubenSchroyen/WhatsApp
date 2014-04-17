package worms.model;

 
/**
 * A class of worms involving a position consisting of an X- and Y-coordinate, an angle,
 * a radius, a mass, action points (current and maximum)
 *
 * @version 1.0
 * @Author Ruben Schroyen & Ralph Vancampenhoudt
 * 
 * @invar isValidRadius(getRadius())
 * 
 * @invar isValid
 */
 
import java.lang.Math;
import java.util.Arrays;
import java.util.List;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Model;
import be.kuleuven.cs.som.annotate.Raw;
import worms.util.Util;
 
public class Worm {                    
	
	
         /**
         *   The X-coordinate for the worm in meters
         **/
	     private double posX;
	
	     
	     /**
	     *   The Y-coordinate for the worm in meters
	     **/
	     private double posY;
	     
	     
	     /**
	      *  The angle the worm is facing in radial
	      **/
	     private double angle;
	     
	     
	     /**
	      *   The radius of a worm in meter
	      */
	     private double radius;
	     
	     
	     /**
	      *   The minimal radius a worm has to be in meter (0.25m)
	      */
	     public final double minRadius = 0.25;
	     
	     
	     /**
	      *   The mass a worm has in kilogram
	      */
	     private double mass;
	     
	     
	     /**
	      *   The density a worm has (1062kg/m�)
	      */
	     private static final double density = 1062;

	     
	     /**
	      *   The current amount of action points a worm has
	      */
	     private int currentAP;
	     
	     
	     /**
	      *   The maximum amount of action points a worm has
	      */
	     private int maxAP;

	     
	     
	     /**
	      *   The name a worm has been given
	      */
	     private String name;
	     
	     
	     /**
	      *   The earth acceleration (9.80665)
	      */
         private final double g = 9.80665;
	     
	     
         /**
	      *   The force with which a worm jumps
	      */
	     private double force;
	     
	     
	     /**
	      *   The velocity with which a worm jumps
	      */
	     private double velocity;
	     
	     
	     /**
	      *   The distance a worm jumps in meter
	      */
	     private double distance;
	     
	     
	     /**
	      *   The time a worm is in the air
	      */
	     private double time;

         
	     /**
	      * Creates the worm with some initialization parameters.
	     * @param x
	      * 			creates the worm in a position on the X-axis of the world
	      * 
	      * @param y
	      * 			creates the worm in a position on the Y-axis of the world
	      * 
	      * @param angle
	      * 			creates the worm with an initial angle of direction
	      *
	      * @param radius
	      * 			creates the worm with an initial radius
	      * 
	      * @param name 
	      * 			creates the worm with a given name
	      * 
	      * @pre given radius must be valid
	      * 		| isValidRadius(radius)
	      * 
	      * @pre given name must be valid
	      * 		| isValidName(name)
	      * 
	      * @pre given orientation is valid
	      * 		| isValidAngle(angle)
	      * 
	      * @post X-coordinate is set to given value
	      * 		| new.getPosX() == x
	      * 
	      * @post Y-coordinate is set to given value
	      * 		| new.getPosY() == y
	      * 
	      * @post radius of the worm is set to given value
	      * 		| new.getRadius() == radius
	      * 
	      * @post name of the worm is set to the given string if name is valid
	      * 		| new.getName() == name
	      * 
	      * @post number of action points is set to the maximum number of action points a worm can have 
	      * 		| new.getCurrentAP() == this.getMaxAP()
	      * 
	      * @post number of hitpoints is set to the maximum number of hitpoints a worm can have
	      * 		| new.getHP() == this.getMaxHP()
	      */ 
        public Worm(double x, double y, double radius, double angle, String name)
        {
                this.setPosX(x);
                this.setPosY(y);
                this.setRadius(radius);
                this.setAngle(angle);
                this.setName(name);
                this.setCurrentAP(this.getMaxAP());
                this.setHP(this.getMaxHP());
                this.setTeam(team);
        }
        
        /**
         * Method to calculate the cost of moving in the direction the worm is facing
         * 
         * @param distance
         * 		Amount of steps the worm will move in that direction
         * 
         * @return
         * 		The cost of the movement
         * 			| cost = (int) Math.ceil( (|cos(angle)| + 4*|sin(angle)|) * nbSteps
         */
        public int calculateApCostMove (double distance) 
        {
                int cost = (int) Math.ceil((Math.abs(Math.cos(this.getAngle())) + 4*Math.abs(Math.sin(this.getAngle())))*distance);
                return cost;
        }
       
        
        /**
         * Method to calculate the cost of changing the angle of the worm
         * 
         * @param angle
         * 		The new angle the worm will be facing
         * 
         * @return
         * 		The cost of the turn
         * 			| cost = (int) Math.ceil( |angle|*60 / 2*PI )
         */
        public int calculateApCostTurn (double angle)
        {
        	int cost = (int) Math.ceil(Math.abs(angle)*60/(2*Math.PI));
        	return cost;
        }
       
                       
       
		/**
		 * Method to inspect whether the movement we try to make is a valid one
		 * 
		 * @param nbSteps
		 * 		Amount of steps the worm tries to move
		 * 
		 * @return
		 * 		true if the move is valid
		 * 
		 * @throws IllegalArgumentException
		 * 		- If the amount of steps is smaller than zero
		 * 			| nbSteps < 0
		 * 		- If the cost of the movement is bigger than the current amount of AP
		 * 			| calculateApCostMove(nbSteps) > this.getCurrentAP()
		 */
		public boolean isValidMovement() throws IllegalArgumentException
		{
            if (this.maxDistance(this.getAngle(), this.getRadius()) < 0)
            	throw new IllegalArgumentException("Not a valid amount for distance");
            if (calculateApCostMove(this.maxDistance(this.getAngle(), this.getRadius())) > this.getCurrentAP())
            	throw new IllegalArgumentException("Not enough AP");
            return true;          
		    }
		   
		/**
		 * Method to inspect whether the turn we try to make is a valid one
		 * 
		 * @param angle
		 * 		The angle we try to achieve
		 * 			If it surpasses the value of PI, we reset it to PI
		 * 			If it surpasses the value of -PI, we reset it to -PI
		 * 
		 * @return
		 * 		true if the turn is valid
		 * 
		 * @throws IllegalArgumentException
		 * 		If the cost of the turn is bigger than the current amount of AP
		 * 			| calculateApCostTurn(angle) < this.getCurrentAP()
		 */
		public boolean isValidTurn(double angle) throws IllegalArgumentException 
		{
			if (angle > Math.PI)
				angle = Math.PI;
			if (angle < -Math.PI)
				angle = -Math.PI;
			if (this.getCurrentAP() < calculateApCostTurn(angle))
				throw new IllegalArgumentException("Not enough AP");
		    return true;
		}            
		   
		    
		/**
		 * Method to inspect whether the radius we try to make is a valid one
		 * 
		 * @param radius
		 * 		The radius of a worm
		 * 
		 * @return
		 * 		true if the radius is valid
		 * 
		 * @throws IllegalArgumentException
		 * 		If the radius is smaller than the minimum radius or equal to infinity (because there is a minimal radius, -infinity is not applicable)
		 */
		public boolean isValidRadius(double radius) throws IllegalArgumentException
		{
		   if ((radius < minRadius) && (Double.POSITIVE_INFINITY == radius))
		      throw new IllegalArgumentException("Not a valid value for radius");
		   return true;
	    }
		   
		    
		/**
		 * Method to inspect whether the name we try to give to the worm is a valid one
		 * 
		 * @param name
		 * 		The name of the worm
		 * 
		 * @return
		 * 		true if the name is valid
		 * 
		 * @throws IllegalArgumentException
		 * 		- If the name is shorter than 2 characters
		 * 			| !name.length() > 2
		 * 		- If the name does not start with an uppercase character
		 * 			| !Character.isUpperCase(name.charAt(0))
		 * 		- If the name does not consist of letters, spaces, single or double quotes
		 * 			| !name.matches("[a-zA-Z'\" ]*"))
		 */
		public boolean isValidName(String name) throws IllegalArgumentException 
		{
		        if (name.length() < 2)
		        	throw new IllegalArgumentException("Not enough characters");
		       
		        if (!Character.isUpperCase(name.charAt(0)))
		        	throw new IllegalArgumentException("Not starting with uppercase character");
		       
		        if (!name.matches("[a-zA-Z'\" ]*"))
		        	throw new IllegalArgumentException("Not a valid character");
		        return true;
		                               
		 }
		 
		                       
		/**
		 * Method to inspect whether the position of a worm we try to give him is a valid one
		 * 
		 * @param posX
		 * 		The position of the worm on the X-axis
		 * 
		 * @param posY
		 * 		The position of the worm on the Y-axis
		 * 
		 * @return
		 * 		true if the value of position is a valid one
		 * 
		 * @throws IllegalArgumentException
		 * 		If the position of either X or Y is equal to infinity (both +infinity and -infinity)
		 * 			| posX == Double.NEGATIVE_INFINITY
		 * 			| posY == Double.NEGATIVE_INFINITY
		 * 			| posX == Double.POSITIVE_INFINITY
		 * 			| posY == Double.POSITIVE_INFINITY
		 */
		public boolean isValidPosition(double posX, double posY) throws IllegalArgumentException 
		{
		        if ((posX == Double.NEGATIVE_INFINITY) || (posY == Double.NEGATIVE_INFINITY) || (posX == Double.POSITIVE_INFINITY) || (posY == Double.POSITIVE_INFINITY))
		        	throw new IllegalArgumentException("Not a valid value for position");
		            return true;
		}
		   
		    
		   
		/**
		 * Method to inspect whether the mass of the worm is a valid one
		 * 
		 * @param mass
		 * 		The mass of a worm
		 * 
		 * @return
		 * 		true if the value of mass is a valid one
		 * 
		 * @throws IllegalArgumentException
		 * 		- If the mass of a worm is smaller than density * (4/3) * PI * minRadius�
		 * 			| (density * (4/3) * Math.PI * Math.pow(minRadius,3) > mass
		 * 		- If the mass is equal to infinity (both +infinity and -infinity)
		 * 			| mass == Double.NEGATIVE_INFINITY
		 * 			| mass == Double.POSITIVE_INFINITY
		 */
		public boolean isValidMass(double mass) throws IllegalArgumentException 
		{
		        if ((density * (4/3) * Math.PI * Math.pow(minRadius,3) > mass) || (mass == Double.NEGATIVE_INFINITY) || (mass == Double.POSITIVE_INFINITY) )
		        	throw new IllegalArgumentException("Not a valid value for mass");
		            return true;
		}
		   
		    
		   
		/**
		 * Method to inspect whether the amount of current AP is a valid amount
		 * 
		 * @param currentAP
		 * 		The current amount of AP of the worm
		 * 
		 * @return
		 * 		true if the value of currentAP is a valid one
		 * 
		 * @throws IllegalArgumentException
		 * 		If the amount of currentAP is smaller than zero and bigger than the maximum amount of AP possible
		 * 			| currentAP < 0 
		 * 			| currentAP > this.getMaxAP()
		 */
		public boolean isValidAP(double currentAP) throws IllegalArgumentException 
		{
		        if ((0 > currentAP) && (currentAP > this.getMaxAP()))
		        	throw new IllegalArgumentException("Not a valid value for AP");
		                                return true;
		}


		   
		 
		   
		/**
		 * The method to turn the worm to a certain new direction if he has enough action points to do so.
		 *
		 * @param newangle
		 *  	The new angle a worm gets after changing it in game. This new angle is used to calculate the cost of this turn
		 * 
		 * @throws IllegalArgumentException
		 * 		If the worm does not have enough AP to do this turn
		 *      | !isValidTurn()
		 *
		 * @post
		 *      If the worm has enough AP to do so, he will turn and change his angle to the new angle, reducing his AP by the calculated amount in calculateApCostTurn.
		 *      To make sure that a worm also loses AP when the new angle is negative, the absolute value of this angle-difference is taken to calculate its cost.
		 *      | new.getAngle() == angle  
		 *      | new.getCurrentAP() == currentAP   
		 */
		public void Turn(double newangle) throws IllegalArgumentException 
		{
		        if (isValidTurn(newangle) != true )
		                throw new IllegalArgumentException("Insufficient Action Points");
		                   
		            this.angle = this.getAngle() + newangle;
		            this.currentAP = this.getCurrentAP() - calculateApCostTurn(Math.abs(newangle - this.angle));    
		                   
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
        	if (this.canJump())
        	{	
        		while (world.isPassable(this.getPosX(), this.getPosY(), this.getRadius()))
        		{
                	double [] newPosition = this.JumpStep(this.JumpTime());
                	this.setPosX(newPosition[0]);
                	this.setPosY(newPosition[1]);
                	this.setCurrentAP(0);
        		}
        	}
        	consume();
        }
        
        public boolean canJump()
        {
        	if (this.getCurrentAP() > 0 && world.isPassable(this.getPosX(), this.getPosY(), this.getRadius()))
        		return true;
        	return false;
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
		 * 		| new.getDistance() == ((this.getVelocity� * sin(2*this.getAngle()) / g)
		 * 		| new.getTime() == (this.getDistance() / (this.getVelocity() * cos(this.getAngle()) )
		 * 
		 * 		If the worm does not have enough AP, we set the amount of time in the air to zero
		 * 		| new.getTime() == 0.0
		 */
        public double JumpTime()
        {
		    	if (this.getCurrentAP() != 0)
		    	{
		        	this.setForce((5 * this.getCurrentAP()) + (this.getMass() * g));
		            this.setVelocity(this.getForce()/this.getMass()*0.5);
		            this.setDistance( (Math.pow(this.getVelocity(), 2) * Math.sin(2*this.getAngle()) ) / g);
		            this.setTime(this.getDistance() / (this.getVelocity() * Math.cos(this.getAngle()) ) );
		            return this.getTime();
		        }
		    else 
		    {
		    	return 0.0;
		    }
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
		        double velocityX = this.getVelocity() * Math.cos(this.getAngle());
		        double velocityY = this.getVelocity() * Math.sin(this.getAngle());
		        double x = this.getPosX() + (velocityX * DeltaT);
		        double y = this.getPosY() + (velocityY * DeltaT - 0.5*g*Math.pow(DeltaT, 2));
		        double jumpstep[] = new double[] {x,y};
		        
		        if (Util.fuzzyLessThanOrEqualTo(0, this.getAngle()) && (Util.fuzzyLessThanOrEqualTo(this.getAngle(), Math.PI)))
		        	return jumpstep;
		        
		        else 
		        {
		        	double[] original = new double[] {this.getPosX(),this.getPosY()};
		        	return original;
		        }
		    
		    }
		    

		/**
		 * This method recalls the value of the maximum amount of AP a worm can have, namely the mass a worm has rounded upwards to the next integer
		 * 
		 * @return Max_AP
		 * 		Equal to this.getMass() rounded upwards to the next integer
		 * 		| (int)Math.ceil(this.getMass())
		 */
        @Basic @Raw
	    public int getMaxAP() 
        {
	            return maxAP;
	    }
		   
		    
		/**
		 * This method recalls the value of the angle the worm is facing
		 */
        @Basic @Raw
		public double getAngle() 
        {
		        return angle;
		}
		
		/**
		 * This method sets the value for the angle a worm is facing. If the angle surpasses the value of PI, the angle gets reset by extracting PI
		 * 
		 * @param angle
		 * 		The angle of direction a worm is facing
		 * 			If angle surpasses the value of PI, it gets reset to the angle - the amount of times PI goes in the angle
		 * 				| this.angle = angle - k*Math.PI
		 * 			If angle surpasses the value of -PI, it gets reset to the angle + the amount of times PI goes in the angle
		 * 				| this.angle = angle + k*Math.PI
		 * 
		 * @post 
		 * 		Sets the value of the worms angle to a newly calculated or given angle
		 * 		| new.getAngle() == angle
		 */
        @Basic @Model
	    public void setAngle(double angle) 
        {
        	if (angle > Math.PI)
				angle = Math.PI;
			if (angle < -Math.PI)
				angle = -Math.PI;
        	this.angle = angle;
		}
		   
		   
		    
		/**
		 * This method recalls the value of the radius of a worm
		 */
        @Basic @Raw
		public double getRadius() 
        {
		        return radius;
		}
		
		/**
		 * This method sets the value for the radius of the worm
		 * 
		 * @param radius
		 * 		The radius a worm has been given in meters
		 * 
		 * * @throws IllegalArgumentException
		 * 		If the value for radius is not a valid one
		 * 		| !isValidRadius(radius)
		 * 
		 * @post 
		 * 		Sets the value of the worms radius to a newly calculated or given radius
		 * 		| new.getRadius == radius
		 */
		@Basic @Model
		public void setRadius(double radius) throws IllegalArgumentException 
		{
			if (!isValidRadius(radius))
					throw new IllegalArgumentException("Radius is not valid");
	        this.radius = radius;
		}
		
		
		
		/**
		 * This method recalls the value of the mass a worm has, equal to density*(4/3)*Math.PI*Math.pow(getRadius(), 3)
		 */
		@Basic @Raw
	    public double getMass() 
		{
	       return mass;
		}
		  
		   
		    
		/**
		 * This method recalls the X-position of the worm
		 */
		@Basic @Raw
		public double getPosX() 
		{
		        return posX;
		}
		
		/**
		 * This method sets the value for the X-position of the worm
		 * 
		 * @param xx
		 * 		The X-position of the worm in meters
		 * 
		 * @throws IllegalArgumentException
		 * 		If the value for x is not a valid one
		 * 		| !isValidPosition(x,this.getPosY())
		 * 
		 * @post 
		 * 		Sets the value of the worms X-position to a newly calculated or given X-position
		 * 		| new.getPosX() == x
		 */
		@Basic @Model
	    public void setPosX(double x) throws IllegalArgumentException 
	    {
			if (!isValidPosition(x,this.getPosY()))
				throw new IllegalArgumentException("Position is not valid");
	        this.posX = x;
		}
		   
		   
		    
		/**
		 * This method recalls the Y-position of the worm
		 */
		@Basic @Raw
		public double getPosY() 
		{
		        return posY;
		}
		
		/**
		 * This method sets the value for the Y-position of the worm
		 * 
		 * @param y
		 * 		The Y-position of the worm in meters
		 * 
		 * @throws IllegalArgumentException
		 * 		If the value for y is not a valid one
		 * 		| !isValidPosition(this.getPosX(),y)
		 * 
		 * @post 
		 * 		Sets the value of the worms Y-position to a newly calculated or given Y-position
		 * 		| new.getPosY() == y
		 */
		@Basic @Model
	    public void setPosY(double y) throws IllegalArgumentException
	    {
			if (!isValidPosition(this.getPosX(),y))
					throw new IllegalArgumentException("Position is not valid");
	        this.posY = y;
		}
		   
		   
		    
		/**
		 * This method recalls the current amount of AP a worm has
		 */
		@Basic @Raw
		public int getCurrentAP() {
		        return currentAP;
		}
		
		/**
		 * This method sets the value for the current amount of AP a worm has
		 * 
		 * @param current_AP
		 * 		The current amount of AP a worm has
		 * 
		 * @throws IllegalArgumentException
		 * 		If the value of currentAP is not a valid one
		 * 		| !isValidAP(currentAP)
		 * 
		 * @post
		 * 		Sets the value of the worms current AP to the newly calculated or given current AP
		 * 		| new.getCurrentAP() == currentAP
		 */
		@Basic @Model
	    public void setCurrentAP(int currentAP) throws IllegalArgumentException
	    {
			if (!isValidAP(currentAP))
				throw new IllegalArgumentException("Current AP is not valid");
			this.currentAP = currentAP;
		}
		   
		   
		    
		/**
		 * This method recalls the given name of the worm
		 */
		@Basic @Raw
		public String getName() 
		{
		        return name;
		}
		
		/**
		 * This method sets the name of the worm
		 * 
		 * @param name
		 * 		The name a worm is given
		 * 
		 * @throws IllegalArgumentException
		 * 		If the name of the worm does not follow his constraints of being longer than 2 characters, starting with an uppercase character and consisting
		 * 		only of letters, single or double quotes and spaces
		 * 		| !isValidName(name)
		 * 
		 * @post
		 * 		Sets the value of the worms name to a newly given name if this name is valid
		 * 		| new.getName() == name
		 */
		@Basic @Model
		public void setName (String name) throws IllegalArgumentException 
		{
		      if (isValidName(name) == false)
		        throw new IllegalArgumentException("Your name has some invalid characters included");
		      this.name = name;
		}                      
		   
		    
		/**
		 * This method recalls the force with which a worm jumps
		 */
		@Basic @Raw
		public double getForce() 
		{
		        return force;
		}
		
		/**
		 * This method sets the value for the force with which a worm jumps
		 * 
		 * @param force
		 * 		The force with which a worm jumps
		 * 
		 * @post 
		 * 		Sets the value of the force with which a worm jumps to the newly calculated or given force
		 * 		| new.getForce() == force
		 */
		@Basic @Model
	    public void setForce(double force) 
		{
	            this.force = force;
		}
		   
		   
		    
		/**
		 * This method recalls the value of the velocity with which a worm jumps
		 */
		@Basic @Raw
		public double getVelocity() 
		{
		        return velocity;
		}
		
		/**
		 * This method sets the value for the velocity with which a worm jumps
		 * 
		 * @param velocity
		 * 		the velocity with which a worm jumps
		 * 
		 * @post
		 * 		Sets the value of velocity with which a worm jumps to the newly calculated or given velocity
		 * 		| new.getVelocity() == velocity
		 */
		@Basic @Model
	    public void setVelocity(double velocity) 
		{
	        this.velocity = velocity;
		}
		   
		   
		    
		/**
		 * This method recalls the value of the distance a worm jumps
		 */
		@Basic @Raw
		public double getDistance() 
		{
		    return distance;
		}
		
		/**
		 * This method sets the value for the distance a worm jumps
		 * 
		 * @param distance
		 * 		the distance a worm jumps
		 * 
		 * @post
		 * 		Sets the value of distance a worm jumps to the newly calculated or given distance
		 * 		| new.getDistance() == distance
		 */
		@Basic @Model
	    public void setDistance(double distance) 
		{
	       this.distance = distance;
		}
		   
		   
		    
		/**
		 * This method recalls the value of the time a worm is in the air while jumping
		 */
		@Basic @Raw
		public double getTime() 
		{
		    return time;
		}
		
		/**
		 * This method sets the value for the time a worm is in the air while jumping
		 * 
		 * @param time
		 * 		The time a worm is in the air while jumping
		 * 
		 * @post 
		 * 		Sets the value of the time a worm is in the air while jumping to the newly calculated or given time
		 * 		| new.getTime() == time
		 */
		@Basic @Model
		public void setTime(double time) 
		{
		        this.time = time;
		}
                        
/*********************************************************************************************
 * 																						     *
 * 									   PART 2 ADDITIONS									     *
 * 																						     *       
 *********************************************************************************************/
                        
		public int currentHP;
		public boolean outOfBounds = false;
		public List<String> weapons = Arrays.asList("Bazooka", "Rifle");
		public String selectedWeapon = "Bazooka";
		public World world;
		public Team team;
		public int index = 0;
		public Projectile projectile;
		private Food food;

		
		public void setSelectedWeapon(String selectedweapon)
		{
			this.selectedWeapon = selectedweapon;
		}
		
		public String getSelectedWeapon() 
		{
			
			return selectedWeapon;
		}
		
		public int getMaxHP() 
		{
		    return (int)Math.ceil(this.getMass());
		}
		
		public int getHP() 
		{
			return currentHP;
		}
		
		public boolean isAlive() 
		{
			return (isValidHP(this.getHP()) && world.wormInBounds(this) && outOfBounds);
		}
		
		public void setHP(int HP) 
		{
			if (isValidHP(HP))
				this.currentHP = HP;
		}
		
		public boolean isValidHP(int hp)
		{
			if (hp < 0 || hp >= Double.POSITIVE_INFINITY)
				return false;
			return true;
		}

		public World getWorld() 
		{
			return world;
		}

		public Team getTeam() 
		{
			return team;
		}
		
		public void setWorld(World world)
		{
			this.world = world;
		}
		
		public void setTeam(Team team)
		{
			this.team = team;
		}

		public void selectNextWeapon() 
		{
			weapons.get(index ++);		
		}

		public void shoot(int propulsionYield) 
		{
			if (this.canShoot())
			{
				if (this.getSelectedWeapon() == "Bazooka")
				{
					projectile.shootBazooka(propulsionYield);
					this.setCurrentAP(getCurrentAP() - 50);
				}
				
				if (this.getSelectedWeapon() == "Rifle")
				{
					projectile.shootRifle();
					this.setCurrentAP(getCurrentAP() - 10);
				}
			}
		}
		
		public void nextTurn()
		{
			if (world.currentWorm() != this)
				this.setHP(getHP() + 10);
				this.setCurrentAP(getMaxAP());
		}

		public void setMass(double mass) 
		{
			this.mass = density*(4/3)*Math.PI*Math.pow(getRadius(), 3);
		}

		public void setMaxAP(int maxAP) 
		{
			this.maxAP = (int)Math.ceil(this.getMass());
		}

		public boolean canShoot()
		{
			if (world.isPassable(this.getPosX(), this.getPosY(), this.getRadius()))
			{
				if (this.getSelectedWeapon() == "Bazooka" && this.getCurrentAP() >= 50)
					return true;
				if (this.getSelectedWeapon() == "Rifle" && this.getCurrentAP() >= 10)
					return true;
				return false;
			}	
			return false;
		}
		

		private void consume() 
		{
			if (this.getPosX() == food.getPosX() || this.getPosY() == food.getPosY())
			{
				this.setRadius(this.getRadius()*1.1);
				food.eaten = true;
			}
			
		}

		public void fall() 
		{
			if (canFall()) 
			{
				double fallTime = fallTime();
				if (fallTime == Double.MAX_VALUE) 
				{
					outOfBounds = true;
				} 
				else 
				{
					double fallDistance = Math.abs(getPosY() - fallDistance(fallTime));
					this.setHP((int)Math.floor(fallDistance) * 3);
					setPosY(getPosY() - fallDistance);
				}
			}
		}

		
		public double fallDistance(double time) 
		{
			 return ((1/2) * g * time * time);
		}

		public double fallTime() 
		{
			double timeStep = 0.01;
			double time = 0.0;
			while (true) 
			{
				double target = getPosY() - fallDistance(time);
				if (!isValidPosition(getPosX(), target))
					return Double.MAX_VALUE;
				if (!isValidPosition(getPosX(), target) || world.isAdjacent(getPosX(), target, getRadius()))
					return time;
				time += timeStep;
			}
		}

		public boolean canFall() 
		{
			return !world.isAdjacent(this.getPosX(), this.getPosY(), getRadius());
		}
		
		public void destroy()
		{
			if (!isAlive())
				world.removeWorm(this);
		}

		
		public void move() throws IllegalArgumentException 
		{
			if (this.isValidMovement()) {
				double deltaTime = 0.0175; 
				double minDistance = 0.1;
				double[] temp = new double[]{0.0, this.getAngle()}; 
				for (double counter = 0.0; counter < 0.7875; counter += deltaTime)
				{
					double maxDistanceClockwise = maxDistance(this.getAngle() + counter, minDistance);
					if (maxDistanceClockwise == getRadius()) 
					{
						temp = new double[] {getRadius(), this.getAngle() + counter };
						break;
					} 
					
					else 
					{
						if (maxDistanceClockwise > temp[0]) 
						{
							temp = new double[] {maxDistanceClockwise, this.getAngle() + counter };
							minDistance = maxDistanceClockwise;
						}
					}
					
					double maxDistanceCounterClockwise = maxDistance(this.getAngle() - counter, minDistance);
					if (maxDistanceCounterClockwise == getRadius()) 
					{
						temp = new double[] {getRadius(), this.getAngle() - counter };
						break;
					} 
					
					else 
					{
						if (maxDistanceCounterClockwise > temp[0])
						{
							temp = new double[] {maxDistanceCounterClockwise, this.getAngle() - counter };
							minDistance = maxDistanceCounterClockwise;
						}
					}
				}
				
				setPosX(getPosX() + Math.cos(temp[1]) * temp[0]);
				setPosY(getPosY() + Math.sin(temp[1]) * temp[0]);
				if (!isValidPosition(this.getPosX(), this.getPosY()))
					outOfBounds = true;
				fall();
				consume();
			} 
			
			else 
				throw new IllegalArgumentException("Can't move that far");
		}
		
		
		public double maxDistance(double angle, double minDistance)
		{
			double delta = 0.001;
			for (double distance = this.getRadius(); distance > minDistance; distance -= delta)
			{
				double X = this.getPosX() + Math.cos(angle)*distance;
				double Y = this.getPosY() + Math.sin(angle)*distance;
					if (world.isAdjacent(X, Y, this.getRadius()))
						return distance;
			}
			return 0;
		}		
		
}
                               