package worms.model;
 
import static org.junit.Assert.*;
 
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
 
import org.junit.Test;
import org.junit.Before;
 
public class WorldTest {
       
        private static boolean [][] passablemap = new boolean[][] { { false, false, false, false, false }, { false, true, true,true,false }, { false, true, true,true,false }, { false, true, true,true,false }, { false, false, false, false, false } };
        private static Random random;
        World testWorld;
       
        Worm testWorm1 = new Worm(testWorld, 1.0,1.0,1,Math.PI+1,"EersteWorm");//incl
    Worm testWorm2 = new Worm(testWorld, 1.0,1.0,1,0,"TweedeWorm");                //incl
    Worm testWorm3 = new Worm(testWorld, 1.0,1.0,1,0,"DerdeWorm");                 //niet incl
    Worm testWormNull;                                                                                  //null
   
    Food testFood1 = new Food(testWorld,2,2); //incl
    Food testFood2 = new Food(testWorld,4,4); //niet incl
    Food testFoodNull;                                            //null
   
    Projectile testProjectile1 = new Projectile(testWorm1); //incl
    Projectile testProjectile2 = new Projectile(testWorm2); //niet incl
    Projectile testProjectileNull;                                        //null
   
    Team testTeam1 = new Team("TestTeam1",testWorld);   //incl
    Team testTeam2 = new Team("TestTeam2",testWorld);   //niet incl
    Team testTeamNull;                                                                  //null
   
    public List<Worm> allWorms = new ArrayList<Worm>(); //TODO add included stuff
    public List<Food> allFood = new ArrayList<Food>();
        public List<Projectile> allProjectiles = new ArrayList<Projectile>();
        public List<Team> allTeams = new ArrayList<Team>();
   
       
         
       
        @Before
        public void setupTestWorld() {
                testWorld = new World(5.0, 5.0, passablemap, random);
                testWorld.worms = allWorms;
                testWorld.fodder = allFood;
                testWorld.projectiles = allProjectiles;
                testWorld.teams = allTeams;
        }

 
        @Test
        public final void testCurrentWorm() {
                testWorld.setIndex(1);
                                assertEquals(testWorld.worms.get(1),testWorld.currentWorm());
        }
 
        @Test
        public final void testNextWorm() {
                testWorld.setIndex(0);
                assertEquals(testWorld.worms.get(1),testWorld.currentWorm());
        }
 
 
        @Test
        public final void testIsFinished() {
                testWorld.teams = new ArrayList<Team>(2);  
                assertEquals( false, testWorld.isFinished() );
                testWorld.teams = new ArrayList<Team>(1);  
                assertEquals( true, testWorld.isFinished() );
        }
 
        @Test
        public final void testWormExists() {
                assertEquals(true, testWorld.wormExists(testWorm1));
                assertEquals(false,testWorld.wormExists(testWormNull));
        }
 
        @Test
        public final void testWormInWorld() {
                assertEquals(true, testWorld.wormInWorld(testWorm1));
                assertEquals(false, testWorld.wormInWorld(testWorm3));
        }
 
        @Test
        public final void testAddWorm() {
                final Worm testWorm = new Worm(testWorld, 1.0,1.0,1,Math.PI+2,"TestWorm");
                testWorld.addWorm();
                assertEquals(true,testWorld.wormInWorld(testWorm));
        }
 
        @Test
        public final void testRemoveWorm() {
                testWorld.removeWorm(testWorm2);
                assertEquals(false,testWorld.wormInWorld(testWorm2));
        }
 
        @Test
        public final void testFoodExists() {
                assertEquals(true, testWorld.foodExists(testFood1));
                assertEquals(true, testWorld.foodExists(testFood2));
                assertEquals(false, testWorld.foodExists(testFoodNull));
        }
 
        @Test
        public final void testFoodInWorld() {
                assertEquals(true, testWorld.foodInWorld(testFood1));
                assertEquals(false, testWorld.foodInWorld(testFood2));
        }
 
        @Test
        public final void testAddFood() {
                testWorld.addFood();
                assertEquals(true, testWorld.foodInWorld(testFood2));
               
        }
 
        @Test
        public final void testRemoveFood() {
                testWorld.removeFood(testFood1);
                assertEquals(false, testWorld.foodInWorld(testFood1));
        }
 
        @Test
        public final void testProjectileExists() {
                assertEquals(true, testWorld.projectileExists(testProjectile1));
                assertEquals(true, testWorld.projectileExists(testProjectile2));
                assertEquals(false, testWorld.projectileExists(testProjectileNull));
        }
 
        @Test
        public final void testProjectileInWorld() {
                assertEquals(true, testWorld.projectileInWorld(testProjectile1));
                assertEquals(false, testWorld.projectileInWorld(testProjectile2));
        }
 
        @Test
        public final void testAddProjectile() {
                testWorld.addProjectile(testProjectile2);
                assertEquals(true, testWorld.projectileInWorld(testProjectile2));
        }
 
        @Test
        public final void testRemoveProjectile() {
                testWorld.removeProjectile(testProjectile1);
                assertEquals(false, testWorld.projectileInWorld(testProjectile1));
        }
 
        @Test
        public final void testTeamExists() {
                assertEquals(true,testWorld.teamExists(testTeam1));
                assertEquals(true,testWorld.teamExists(testTeam2));
                assertEquals(false,testWorld.teamExists(testTeamNull));
        }
 
        @Test
        public final void testTeamInWorld() {
                assertEquals(true,testWorld.teamInWorld(testTeam1));
                assertEquals(false,testWorld.teamInWorld(testTeam2));
        }
 
        @Test
        public final void testAddTeam() {
                testWorld.addTeam(testTeam2);
                assertEquals(true,testWorld.teamInWorld(testTeam2));
        }
 
        @Test
        public final void testRemoveTeam() {
                testWorld.removeTeam(testTeam1);
                assertEquals(false,testWorld.teamInWorld(testTeam1));
        }
 
        @Test
        public final void testIsPassable() {
                fail("Not yet implemented"); // TODO donno
        }
 
        @Test
        public final void testGetNextCell() {
                fail("Not yet implemented"); // TODO donno
        }
 
        @Test
        public final void testWormInBounds() {
               
                assertEquals(true,testWorld.wormInBounds(testWorm1));
               
                final Worm testWormOut1 = new Worm(testWorld, 6.0,7.0,1,0,"TestWorm");
                final Worm testWormOut2 = new Worm(testWorld, -6.0,-7.0,1,0,"TestWorm");
               
                assertEquals(false,testWorld.wormInBounds(testWormOut1));
                assertEquals(false,testWorld.wormInBounds(testWormOut2));
        }
 
        @Test
        public final void testFoodInBounds() {
                assertEquals(true,testWorld.foodInBounds(testFood1));
               
                final Food testFoodOut1 = new Food(testWorld,6.0,7.0);
                final Food testFoodOut2 = new Food(testWorld,-6.0,-7.0);
               
                assertEquals(false,testWorld.foodInBounds(testFoodOut1));
                assertEquals(false,testWorld.foodInBounds(testFoodOut2));
        }
 
        @Test
        public final void testProjectileInBounds() {
                assertEquals(true, testWorld.projectileInBounds(testProjectile1));
               
                final Worm testWormOut1 = new Worm(testWorld, 6.0,7.0,1,0,"TestWorm");
                final Worm testWormOut2 = new Worm(testWorld, -6.0,-7.0,1,0,"TestWorm");
                final Projectile testProjectileOut1 = new Projectile(testWormOut1);
                final Projectile testProjectileOut2 = new Projectile(testWormOut2);
       
                assertEquals(false, testWorld.projectileInBounds(testProjectileOut1));
                assertEquals(false, testWorld.projectileInBounds(testProjectileOut2));
        }
 
 
        @Test
        public final void testGetPassableMap() {
                fail("Not yet implemented"); // TODO
        }
 
        @Test
        public final void testIsAdjacent() {
                fail("Not yet implemented"); // TODO donno
        }
 
}