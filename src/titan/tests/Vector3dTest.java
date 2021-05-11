package titan.tests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import titan.Vector3dInterface;
import titan.impl.Vector3d;

public class Vector3dTest {

	@Test
	public void testGetX() {

		Vector3d a = new Vector3d();
		a.setX(1);
		assertEquals(1, a.getX(), 0);
	}

	@Test
	public void testSetX() {

		Vector3d a = new Vector3d();
		a.setX(1);
		assertEquals(1, a.getX(), 0);

	}

	@Test
	public void testGetY() {

		Vector3d a = new Vector3d();
		a.setY(1);
		assertEquals(1, a.getY(), 0);
	}

	@Test
	public void testSetY() {

		Vector3d a = new Vector3d();
		a.setY(2);
		assertEquals(2, a.getY(), 0);
	}

	@Test
	public void testGetZ() {

		Vector3d a = new Vector3d();
		a.setZ(1);
		assertEquals(1, a.getZ(), 0);
	}

	@Test
	public void testSetZ() {

		Vector3d a = new Vector3d();
		a.setZ(3);
		assertEquals(3, a.getZ(), 0);
	}

	@Test
	public void testAdd() {
		Vector3d a = new Vector3d();
		a.setX(1);
		a.setY(2);
		a.setZ(3);
		Vector3d b = new Vector3d();
		b.setX(1);
		b.setY(1);
		b.setZ(1);
		Vector3d newVector = (Vector3d) a.add(b);
		assertEquals(2, newVector.getX(), 0);
		assertEquals(3, newVector.getY(), 0);
		assertEquals(4, newVector.getZ(), 0);

	}

	@Test
	public void testSub() {
		Vector3d a = new Vector3d();
		a.setX(1);
		a.setY(2);
		a.setZ(3);

		Vector3d b = new Vector3d();
		b.setX(1);
		b.setY(1);
		b.setZ(1);
		Vector3d newVector = (Vector3d) a.sub(b);
		assertEquals(0, newVector.getX(), 0);
		assertEquals(1, newVector.getY(), 0);
		assertEquals(2, newVector.getZ(), 0);

	}

	@Test
	public void testMul() {
		Vector3d a = new Vector3d();
		a.setX(1);
		a.setY(2);
		a.setZ(3);
		Vector3d newVector = (Vector3d) a.mul(2);
		assertEquals(2.0, newVector.getX(), 0);
		assertEquals(4.0, newVector.getY(), 0);
		assertEquals(6.0, newVector.getZ(), 0);

	}

	@Test
	public void testAddMul() {
		/*
		 *  Vector3d a = Vector();
		*       double h = 2;
		*       Vector3d b = Vector();
		*       ahb = a.addMul(h, b);
		*       
		* ahb should now contain the result of this mathematical operation:
		*       a+h*b
		 */
		Vector3d a = new Vector3d();
		a.setX(1);
		a.setY(2);
		a.setZ(3);
		Double h = (double) 2;
		Vector3dInterface b = new Vector3d();
		b.setX(1);
		b.setY(1);
		b.setZ(1);
		Vector3d ahb = (Vector3d) a.addMul(h, b);
		assertEquals(3, ahb.getX(), 0);
		//assertEquals(0, 0);

		assertEquals(4, ahb.getY(), 0);
		assertEquals(5, ahb.getZ(), 0);
	}

	@Test
	public void testNorm() {
		/**
		 * @return the Euclidean distance between two vectors
		 */
		Vector3d a = new Vector3d();
		a.setX(1);
		a.setY(2);
		a.setZ(3);

		double norm = a.norm();
		assertEquals(Math.sqrt(14), norm, 0);
	}

	@Test
	public void testDist() {

		Vector3d a = new Vector3d();
		a.setX(1);
		a.setY(1);
		a.setZ(1);
		Vector3d b = new Vector3d();
		b.setX(2);
		b.setY(3);
		b.setZ(2);
		double dist = a.dist(b);
		assertEquals(Math.sqrt(6), dist, 0);
	}

}
