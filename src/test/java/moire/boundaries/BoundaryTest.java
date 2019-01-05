package moire.boundaries;

import static org.junit.Assert.*;

import org.junit.Test;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import moire.builders.BoundaryBuilder;
import moire.builders.BuilderException;

public class BoundaryTest
{
    private static final double TOLLERANCE = 0.01;
    
    private Boundary root;
    private DoubleProperty rootWidth;
    private DoubleProperty rootHeight;
    
    public BoundaryTest ()
    {
        rootWidth = new SimpleDoubleProperty ();
        rootHeight = new SimpleDoubleProperty ();
        
        rootWidth.set ( 100.0 );
        rootHeight.set ( 100.0 );
        
        RootBoundary r = new RootBoundary ();
        
        r.bindWidth ( rootWidth ).bindHeight ( rootHeight );
        
        root = r;
    }
    
    @Test
    public void testRoot ()
    {
        assertEquals (   0.0, root.left (),   TOLLERANCE );
        assertEquals (   0.0, root.top (),    TOLLERANCE );
        assertEquals ( 100.0, root.right (),  TOLLERANCE );
        assertEquals ( 100.0, root.bottom (), TOLLERANCE );
        assertEquals ( 100.0, root.width (),  TOLLERANCE );
        assertEquals ( 100.0, root.height (), TOLLERANCE );
        
        rootWidth.set ( 1000.0 );
        rootHeight.set ( 1000.0 );
        
        assertEquals (    0.0, root.left (),   TOLLERANCE );
        assertEquals (    0.0, root.top (),    TOLLERANCE );
        assertEquals ( 1000.0, root.right (),  TOLLERANCE );
        assertEquals ( 1000.0, root.bottom (), TOLLERANCE );
        assertEquals ( 1000.0, root.width (),  TOLLERANCE );
        assertEquals ( 1000.0, root.height (), TOLLERANCE );
    }
    
    @Test
    public void testAbsolute_LeftRight_TopBottom() throws BuilderException
    {
        Boundary b = new BoundaryBuilder()
                .parent ( root )
                .left ( new Value ( 10.0 ) )
                .top (  new Value ( 20.0 ) )
                .right ( new Value ( 30.0 ) )
                .bottom ( new Value ( 40.0 ) )
                .build();
        
        assertEquals ( 10.0, b.left (),   TOLLERANCE );
        assertEquals ( 20.0, b.top (),    TOLLERANCE );
        assertEquals ( 70.0, b.right (),  TOLLERANCE );
        assertEquals ( 60.0, b.bottom (), TOLLERANCE );
        assertEquals ( 60.0, b.width (),  TOLLERANCE );
        assertEquals ( 40.0, b.height (), TOLLERANCE );
        
        rootWidth.set ( 1000.0 );
        rootHeight.set ( 1000.0 );
        
        assertEquals (  10.0, b.left (),   TOLLERANCE );
        assertEquals (  20.0, b.top (),    TOLLERANCE );
        assertEquals ( 970.0, b.right (),  TOLLERANCE );
        assertEquals ( 960.0, b.bottom (), TOLLERANCE );
        assertEquals ( 960.0, b.width (),  TOLLERANCE );
        assertEquals ( 940.0, b.height (), TOLLERANCE );
    }
    
    @Test
    public void testAbsolute_LeftWidth_TopHeight() throws BuilderException
    {
        Boundary b = new BoundaryBuilder()
                .parent ( root )
                .left ( new Value ( 10.0 ) )
                .top (  new Value ( 20.0 ) )
                .width ( new Value ( 30.0 ) )
                .height ( new Value ( 40.0 ) )
                .build();
        
        assertEquals ( 10.0, b.left (),   TOLLERANCE );
        assertEquals ( 20.0, b.top (),    TOLLERANCE );
        assertEquals ( 40.0, b.right (),  TOLLERANCE );
        assertEquals ( 60.0, b.bottom (), TOLLERANCE );
        assertEquals ( 30.0, b.width (),  TOLLERANCE );
        assertEquals ( 40.0, b.height (), TOLLERANCE );
        
        rootWidth.set ( 1000.0 );
        rootHeight.set ( 1000.0 );
        
        assertEquals ( 10.0, b.left (),   TOLLERANCE );
        assertEquals ( 20.0, b.top (),    TOLLERANCE );
        assertEquals ( 40.0, b.right (),  TOLLERANCE );
        assertEquals ( 60.0, b.bottom (), TOLLERANCE );
        assertEquals ( 30.0, b.width (),  TOLLERANCE );
        assertEquals ( 40.0, b.height (), TOLLERANCE );
    }
    
    @Test
    public void testAbsolute_RightWidth_BottomHeight() throws BuilderException
    {
        Boundary b = new BoundaryBuilder()
                .parent ( root )
                .right ( new Value ( 10.0 ) )
                .bottom (  new Value ( 20.0 ) )
                .width ( new Value ( 30.0 ) )
                .height ( new Value ( 40.0 ) )
                .build();
        
        assertEquals ( 60.0, b.left (),   TOLLERANCE );
        assertEquals ( 40.0, b.top (),    TOLLERANCE );
        assertEquals ( 90.0, b.right (),  TOLLERANCE );
        assertEquals ( 80.0, b.bottom (), TOLLERANCE );
        assertEquals ( 30.0, b.width (),  TOLLERANCE );
        assertEquals ( 40.0, b.height (), TOLLERANCE );
        
        rootWidth.set ( 1000.0 );
        rootHeight.set ( 1000.0 );
        
        assertEquals ( 960.0, b.left (),   TOLLERANCE );
        assertEquals ( 940.0, b.top (),    TOLLERANCE );
        assertEquals ( 990.0, b.right (),  TOLLERANCE );
        assertEquals ( 980.0, b.bottom (), TOLLERANCE );
        assertEquals ( 30.0, b.width (),  TOLLERANCE );
        assertEquals ( 40.0, b.height (), TOLLERANCE );
    }
    
    @Test
    public void testProportional_LeftRight_TopBottom() throws BuilderException
    {
        Boundary b = new BoundaryBuilder()
                .parent ( root )
                .left ( new Value ( 0.07, true ) )
                .top (  new Value ( 0.17, true ) )
                .right ( new Value ( 0.29, true ) )
                .bottom ( new Value ( 0.37, true ) )
                .build();
        
        assertEquals (  7.0, b.left (),   TOLLERANCE );
        assertEquals ( 17.0, b.top (),    TOLLERANCE );
        assertEquals ( 71.0, b.right (),  TOLLERANCE );
        assertEquals ( 63.0, b.bottom (), TOLLERANCE );
        assertEquals ( 64.0, b.width (),  TOLLERANCE );
        assertEquals ( 46.0, b.height (), TOLLERANCE );
        
        rootWidth.set ( 1000.0 );
        rootHeight.set ( 1000.0 );
        
        assertEquals (  70.0, b.left (),   TOLLERANCE );
        assertEquals ( 170.0, b.top (),    TOLLERANCE );
        assertEquals ( 710.0, b.right (),  TOLLERANCE );
        assertEquals ( 630.0, b.bottom (), TOLLERANCE );
        assertEquals ( 640.0, b.width (),  TOLLERANCE );
        assertEquals ( 460.0, b.height (), TOLLERANCE );
    }
    
    @Test
    public void testProportional_LeftWidth_TopHeight() throws BuilderException
    {
        Boundary b = new BoundaryBuilder()
                .parent ( root )
                .left ( new Value ( 0.07, true ) )
                .top (  new Value ( 0.17, true ) )
                .width ( new Value ( 0.29, true ) )
                .height ( new Value ( 0.37, true ) )
                .build();
        
        assertEquals (  7.0, b.left (),   TOLLERANCE );
        assertEquals ( 17.0, b.top (),    TOLLERANCE );
        assertEquals ( 36.0, b.right (),  TOLLERANCE );
        assertEquals ( 54.0, b.bottom (), TOLLERANCE );
        assertEquals ( 29.0, b.width (),  TOLLERANCE );
        assertEquals ( 37.0, b.height (), TOLLERANCE );
        
        rootWidth.set ( 1000.0 );
        rootHeight.set ( 1000.0 );
        
        assertEquals (  70.0, b.left (),   TOLLERANCE );
        assertEquals ( 170.0, b.top (),    TOLLERANCE );
        assertEquals ( 360.0, b.right (),  TOLLERANCE );
        assertEquals ( 540.0, b.bottom (), TOLLERANCE );
        assertEquals ( 290.0, b.width (),  TOLLERANCE );
        assertEquals ( 370.0, b.height (), TOLLERANCE );
    }
    
    @Test
    public void testProportional_RightWidth_BottomHeight() throws BuilderException
    {
        Boundary b = new BoundaryBuilder()
                .parent ( root )
                .right ( new Value ( 0.07, true ) )
                .bottom (  new Value ( 0.17, true ) )
                .width ( new Value ( 0.29, true ) )
                .height ( new Value ( 0.37, true ) )
                .build();
        
        assertEquals ( 64.0, b.left (),   TOLLERANCE );
        assertEquals ( 46.0, b.top (),    TOLLERANCE );
        assertEquals ( 93.0, b.right (),  TOLLERANCE );
        assertEquals ( 83.0, b.bottom (), TOLLERANCE );
        assertEquals ( 29.0, b.width (),  TOLLERANCE );
        assertEquals ( 37.0, b.height (), TOLLERANCE );
        
        rootWidth.set ( 1000.0 );
        rootHeight.set ( 1000.0 );
        
        assertEquals ( 640.0, b.left (),   TOLLERANCE );
        assertEquals ( 460.0, b.top (),    TOLLERANCE );
        assertEquals ( 930.0, b.right (),  TOLLERANCE );
        assertEquals ( 830.0, b.bottom (), TOLLERANCE );
        assertEquals ( 290.0, b.width (),  TOLLERANCE );
        assertEquals ( 370.0, b.height (), TOLLERANCE );
    }
}
