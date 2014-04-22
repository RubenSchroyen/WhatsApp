package worms.model;
 
import static org.junit.Assert.*;
 
import org.junit.*;
 
public class TestWorm {
       
        private static Worm testWorm1;
       
        private static Worm testWorm2;
       
       
        @Before
        public void setUpWorms(){
                testWorm1 = new Worm(null, 1.0,1.0,1,Math.PI+1,"EersteWorm");
                testWorm2 = new Worm(null, 1.0,1.0,1,0,"TweedeWorm");
        }
       
        @Test
        public void testInspectMovementTrue() {
                assertEquals(true, testWorm1.canMove());
        }
       
        @Test
        public void testInspectMovementFalse() {
                testWorm1.setCurrentAP(0);
                assertEquals(false, testWorm1.canMove());
        }
       
        @Test
        public void testInspectTurnTrue(){
                assertEquals(true,testWorm1.isValidTurn(Math.PI/2));
        }
       
        @Test
        public void testInspectTurnFalse(){
                testWorm1.setCurrentAP(0);
                assertEquals(false,testWorm1.isValidTurn(Math.PI/2));
        }
       
        @Test
        public void testInspectRadiusTrue(){
                assertEquals(true,testWorm1.isValidRadius(Math.PI));
        }
       
        @Test
        public void testInspectRadiusFalse(){
                testWorm1.setRadius(0.2);
                assertEquals(false,testWorm1.isValidRadius(testWorm1.getRadius()));
        }
 
        @Test
        public void testInspectRadiusNegative(){
                testWorm1.setRadius(-1);
                assertEquals(false,testWorm1.isValidRadius(testWorm1.getRadius()));
        }
       
        @Test
        public void testInspectNameTrue(){
                assertEquals(true,testWorm1.isValidName(testWorm1.getName()));
        }
       
        @Test
        public void testInspectNameFalseUpperCase(){
                testWorm1.setName("worm");
                assertEquals(false,testWorm1.isValidName(testWorm1.getName()));
        }
       
        @Test
        public void testInspectNameFalseCharacter(){
                testWorm1.setName("Worm1");
                assertEquals(false,testWorm1.isValidName(testWorm1.getName()));
        }
       
        @Test
        public void testInspectNameFalseLength(){
                testWorm1.setName("S");
                assertEquals(false,testWorm1.isValidName(testWorm1.getName()));
        }
       
        @Test
        public void testInspectPositionTrue(){
                assertEquals(true,testWorm1.isValidPosition(testWorm1.getPosX(),testWorm1.getPosY()));
        }
       
        @Test
        public void testInspectPositionFalse(){
                testWorm1.setPosX(Double.NEGATIVE_INFINITY);
                testWorm2.setPosX(Double.POSITIVE_INFINITY);
                testWorm1.setPosY(Double.NEGATIVE_INFINITY);
                testWorm2.setPosY(Double.POSITIVE_INFINITY);
               
                assertEquals(false,testWorm1.isValidPosition(testWorm1.getPosX(),testWorm1.getPosY()));
                assertEquals(false,testWorm2.isValidPosition(testWorm2.getPosX(),testWorm2.getPosY()));
        }
       
        @Test
        public void testInspectActionPointsTrue(){
                assertEquals(true,testWorm1.isValidAP(testWorm1.getCurrentAP()));
        }
       
        @Test
        public void testInspectActionPointsFalse(){
                testWorm1.setCurrentAP(testWorm1.getMaxAP() + 1);
                assertEquals(false,testWorm1.isValidAP(testWorm1.getCurrentAP()));
        }
       
        @Test
        public void testInspectActionPointsNegative(){
                testWorm1.setCurrentAP(-1);
                assertEquals(false,testWorm1.isValidAP(testWorm1.getCurrentAP()));
        }
       
        @Test
        public void testMoveTrue(){
                testWorm1.setAngle(Math.PI/4);
                testWorm1.move();
               
                assertEquals(  testWorm1.getPosX()  ,  ( 1 * Math.cos(Math.PI/4 ) + 1 ) , worms.util.Util.DEFAULT_EPSILON   );
                assertEquals(  testWorm1.getPosY()  ,  ( 1 * Math.sin(Math.PI/4 ) + 1 ) , worms.util.Util.DEFAULT_EPSILON   );
        }
       
        @Test (expected = IllegalArgumentException.class)
        public void testMoveFalse(){
                testWorm1.setCurrentAP(0);
                testWorm1.move();     
        }
       
        @Test
        public void testTurnTrue(){
                testWorm1.Turn(Math.PI/6);
                assertEquals( testWorm1.getAngle() , Math.PI/6 , worms.util.Util.DEFAULT_EPSILON );
               
        }
       
        @Test (expected = IllegalArgumentException.class)
        public void testTurnFalse(){
                testWorm1.setCurrentAP(0);
                testWorm1.Turn(Math.PI);       
        }
       
        @Test
        public void testJumpTrue(){
                testWorm1.setAngle(Math.PI/4);
                double[] landingSpot = testWorm1.JumpStep(testWorm1.JumpTime());
                testWorm1.Jump();
                assertEquals(landingSpot[0], testWorm1.getPosX(),worms.util.Util.DEFAULT_EPSILON );
                assertEquals(landingSpot[1], testWorm1.getPosY(),worms.util.Util.DEFAULT_EPSILON );
                assertEquals(0,testWorm1.getCurrentAP(), worms.util.Util.DEFAULT_EPSILON);
        }
       
        @Test
        public void testJumpFalse(){
                testWorm1.setAngle(Math.PI*(3/4));
                double[] landingSpot = testWorm1.JumpStep(testWorm1.JumpTime());
                testWorm1.Jump();
                assertEquals(landingSpot[0], testWorm1.getPosX(),worms.util.Util.DEFAULT_EPSILON );
                assertEquals(landingSpot[1], testWorm1.getPosY(),worms.util.Util.DEFAULT_EPSILON );
                assertEquals(0,testWorm1.getCurrentAP(), worms.util.Util.DEFAULT_EPSILON);
        }
       
        @Test
        public void testJumpUp(){
                testWorm1.setAngle(Math.PI/2);
                testWorm1.Jump();
                assertEquals(1.0, testWorm1.getPosX(),worms.util.Util.DEFAULT_EPSILON );
                assertEquals(1.0, testWorm1.getPosY(),worms.util.Util.DEFAULT_EPSILON );
                assertEquals(0,testWorm1.getCurrentAP(), worms.util.Util.DEFAULT_EPSILON);
        }
       
        @Test
        public void testGetMaxAP(){
                assertEquals(testWorm1.getMaxAP(), 4447  , worms.util.Util.DEFAULT_EPSILON);
        }
}