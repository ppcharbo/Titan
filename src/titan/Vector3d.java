
package titan;

public class Vector3d implements Vector3dInterface
{
    public Vector3d()
    {
        //MAYBE ADD CONSTRUCTOR VARIABLES test 
    }
    public double getX()
    {
		return 0;
        //test
    }
    public void setX(double x)
    {
        //
    }
    public double getY()
    {
		return 0;
        //
    }
    public void setY(double y)
    {
        //
    }
    public double getZ()
    {
		return 0;
        //
    }
    public void setZ(double z)
    {
        //
    }
    public Vector3dInterface add(Vector3dInterface other)
    {
		return other;

    }
    public Vector3dInterface sub(Vector3dInterface other)
    {
		return other;

    }
    public Vector3dInterface mul(double scalar)
    {
		return null;

    }

    /**
     * Scalar x vector multiplication, followed by an addition
     * 
    * @param scalar the double used in the multiplication step
    * @param other  the vector used in the multiplication step
    * @return the result of the multiplication step added to this vector,
    * for example:
    * 
    *       Vector3d a = Vector();
    *       double h = 2;
    *       Vector3d b = Vector();
    *       ahb = a.addMul(h, b);
    *       
    * ahb should now contain the result of this mathematical operation:
    *       a+h*b
    */
    public Vector3dInterface addMul(double scalar, Vector3dInterface other)
    {
        //we could also use the add and multiple methods that are just above the javadoc here^^^

        double xOtherNew = scalar*(other.getX());
        double yOtherNew = scalar*(other.getY());
        double zOtherNew = scalar*(other.getZ());


        this.setX(this.getX() + xOtherNew);
        this.setY(this.getY() + yOtherNew);
        this.setZ(this.getZ() + zOtherNew);

        return this;
    }
    
    /**
    * @return the Euclidean norm of a vector
    */
    public double norm()
    {
		return 0;
        //
    }
    
    /**
     * @return the Euclidean distance between two vectors
     */
    public double dist(Vector3dInterface other)
    {
		return 0;

    }
    
    /**
    * @return A string in this format: 
    * Vector3d(-1.0, 2, -3.0) should print out (-1.0,2.0,-3.0)
    */
    public String toString()
    {
		return null;

    }
}
