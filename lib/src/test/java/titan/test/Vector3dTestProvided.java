package titan.test;

import titan.Vector3dInterface;
import titan.impl.Vector3d;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class Vector3dTestProvided {

    @Test public void testGetX() {
        Vector3dInterface v = new Vector3d(-1.1, 0.1, 1.1);
        assertEquals(-1.1, v.getX(),0);
    }

    @Test public void testSetX() {
        Vector3dInterface v = new Vector3d();
        v.setX(-1.1);
        assertEquals(-1.1, v.getX(),0);
    }

    @Test public void testGetY() {
        Vector3dInterface v = new Vector3d(-1.1, 0.1, 1.1);
        assertEquals(0.1, v.getY(),0);
    }

    @Test public void testSetY() {
        Vector3dInterface v = new Vector3d();
        v.setY(0.1);
        assertEquals(0.1, v.getY(),0);
    }

    @Test public void testGetZ() {
        Vector3dInterface v = new Vector3d(-1.1, 0.1, 1.1);
        assertEquals(1.1, v.getZ(),0);
    }

    @Test public void testSetZ() {
        Vector3dInterface v = new Vector3d();
        v.setZ(1.0);
        assertEquals(1.0, v.getZ(),0);
    }

    @Test public void testAddVector3d() {
        Vector3dInterface a = new Vector3d(-1.1, 0.1, 1.1);
        Vector3dInterface b = new Vector3d( 0.5, 0.6, 0.7);
        Vector3dInterface ab = a.add(b);
        assertEquals(-1.1+0.5, ab.getX(),0);
        assertEquals( 0.1+0.6, ab.getY(),0);
        assertEquals( 1.1+0.7, ab.getZ(),0);
    }

    @Test public void testSub() {
        Vector3dInterface a = new Vector3d(-1.1, 0.1, 1.1);
        Vector3dInterface b = new Vector3d( 0.5, 0.6, 0.7);
        Vector3dInterface ab = a.sub(b);
        assertEquals(-1.1-0.5, ab.getX(),0);
        assertEquals( 0.1-0.6, ab.getY(),0);
        assertEquals( 1.1-0.7, ab.getZ(),0);
    }

    @Test public void testMul() {
        Vector3dInterface a = new Vector3d(-1.1, 0.1, 1.1);
        Vector3dInterface b = a.mul(0.5);
        assertEquals(-1.1*0.5, b.getX(),0);
        assertEquals( 0.1*0.5, b.getY(),0);
        assertEquals( 1.1*0.5, b.getZ(),0);
    }

    @Test public void testAddMul() {
        Vector3dInterface a = new Vector3d( 0.6, 0.7, 0.8);
        Vector3dInterface b = new Vector3d(-1.1, 0.1, 1.1);
        Vector3dInterface ab = a.addMul(0.5, b);
        assertEquals(0.6 + 0.5*(-1.1), ab.getX(),0);
        assertEquals(0.7 + 0.5*0.1,    ab.getY(),0);
        assertEquals(0.8 + 0.5*1.1,    ab.getZ(),0);
    }

    @Test public void testNorm() {
        Vector3dInterface v = new Vector3d(3.0, -2.0, 6.0);
        assertEquals(7.0, v.norm(),0);
    }

    @Test public void testDist() {
        Vector3dInterface a = new Vector3d(3.0, 4.0,  8.0);
        Vector3dInterface b = new Vector3d(0.5, 0.25, 0.5);
        assertEquals(8.75, a.dist(b),0);
    }

    @Test public void testToString() {
        Vector3dInterface v = new Vector3d(-1.1, 2.1, -3.1);
        String stringV = "(-1.1,2.1,-3.1)";
        assertEquals(stringV, v.toString());
    }

}
