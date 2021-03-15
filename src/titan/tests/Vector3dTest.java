package titan.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import titan.Vector3d;

public class Vector3dTest {

	@Test
	public void testVector3d() {
		fail("Not yet implemented");
	}

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
		fail("Not yet implemented");
	}

	@Test
	public void testSub() {
		fail("Not yet implemented");
	}

	@Test
	public void testMul() {
		fail("Not yet implemented");
	}

	@SuppressWarnings("deprecation")
	@Test
	public void testAddMul() {
		Vector3d a = new Vector3d();
		a.setX(1);
		a.setY(2);
		a.setZ(3);
		Double h = (double) 2;
		Vector3d b = new Vector3d();
		Vector3d ahb = (Vector3d) a.addMul(h, b);
		assertEquals(2,ahb.getX());
		assertEquals(4,ahb.getY());
		assertEquals(6,ahb.getZ());
	}

	@Test
	public void testNorm() {
		fail("Not yet implemented");
	}

	@Test
	public void testDist() {
		fail("Not yet implemented");
	}

	@Test
	public void testToString() {
		fail("Not yet implemented");
	}

}
