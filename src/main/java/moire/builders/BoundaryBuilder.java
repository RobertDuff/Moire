package moire.builders;

import javafx.beans.binding.Bindings;
import moire.boundaries.Boundary;
import moire.boundaries.Value;

public class BoundaryBuilder
{
    protected Boundary parent;

    protected Value left;
    protected Value top;
    protected Value right;
    protected Value bottom;
    protected Value width;
    protected Value height;

    public BoundaryBuilder parent ( Boundary b )
    {
        parent = b;
        return this;
    }

    public BoundaryBuilder left ( Value v )
    {
        left = v;
        return this;
    }

    public BoundaryBuilder top ( Value v )
    {
        top = v;
        return this;
    }

    public BoundaryBuilder right ( Value v )
    {
        right = v;
        return this;
    }

    public BoundaryBuilder bottom ( Value v )
    {
        bottom = v;
        return this;
    }

    public BoundaryBuilder width ( Value v )
    {
        width = v;
        return this;
    }

    public BoundaryBuilder height ( Value v )
    {
        height = v;
        return this;
    }

    public Boundary build () throws BuilderException
    {
        if ( parent == null )
            throw new BuilderException ( "Boundary must have a parent" );

        int horizontalCount = 0;

        if ( left != null ) horizontalCount++;
        if ( right != null ) horizontalCount++;
        if ( width != null ) horizontalCount++;

        if ( horizontalCount != 2 )
            throw new BuilderException ( "Must specify EXACTLY TWO of Left, Right, and Width" );

        int verticalCount = 0;

        if ( top != null ) verticalCount++;
        if ( bottom != null ) verticalCount++;
        if ( height != null ) verticalCount++;

        if ( verticalCount != 2 )
            throw new BuilderException ( "Must specify EXACTLY TWO of Top, Bottom, and Height" );

        Boundary boundary = new Boundary();

        horizontalBindings ( boundary );
        verticalBindings ( boundary );
        
        return boundary;
    }

    protected void horizontalBindings ( Boundary boundary )
    {
        if ( left != null && right != null )
        {
            final double l = left.value;
            final double r = right.value;

            if ( left.isProportional )
                boundary.leftProperty ().bind ( Bindings.createDoubleBinding ( () -> parent.left() + l * parent.width (), parent.leftProperty (), parent.widthProperty () ) );
            else
                boundary.leftProperty ().bind ( Bindings.createDoubleBinding ( () -> parent.left() + l, parent.leftProperty () ) );

            if ( right.isProportional )
                boundary.rightProperty ().bind ( Bindings.createDoubleBinding ( () -> parent.right() - r * parent.width (), parent.rightProperty (), parent.widthProperty () ) );
            else
                boundary.rightProperty ().bind ( Bindings.createDoubleBinding ( () -> parent.right() - r, parent.rightProperty () ) );

            boundary.widthProperty ().bind ( Bindings.createDoubleBinding ( () -> boundary.right () - boundary.left (), boundary.leftProperty (), boundary.rightProperty () ) );

            return;
        }

        if ( left != null && width != null )
        {
            final double l = left.value;
            final double w = width.value;

            if ( left.isProportional )
                boundary.leftProperty ().bind ( Bindings.createDoubleBinding ( () -> parent.left() + l * parent.width (), parent.leftProperty (), parent.widthProperty () ) );
            else
                boundary.leftProperty ().bind ( Bindings.createDoubleBinding ( () -> parent.left() + l, parent.leftProperty () ) );

            if ( width.isProportional )
                boundary.widthProperty ().bind ( Bindings.createDoubleBinding ( () -> w * parent.width(), parent.widthProperty () ) );
            else
                boundary.widthProperty ().set ( w );

            boundary.rightProperty ().bind ( Bindings.createDoubleBinding ( () -> boundary.left () + boundary.width (), boundary.leftProperty (), boundary.widthProperty () ) );
            
            return;
        }

        // Right and Width

        final double r = right.value;
        final double w = width.value;

        if ( right.isProportional )
            boundary.rightProperty ().bind ( Bindings.createDoubleBinding ( () -> parent.right() - r * parent.width (), parent.rightProperty (), parent.widthProperty () ) );
        else
            boundary.rightProperty ().bind ( Bindings.createDoubleBinding ( () -> parent.right() - r, parent.rightProperty () ) );

        if ( width.isProportional )
            boundary.widthProperty ().bind ( Bindings.createDoubleBinding ( () -> w * parent.width(), parent.widthProperty () ) );
        else
            boundary.widthProperty ().set ( w );

        boundary.leftProperty ().bind ( Bindings.createDoubleBinding ( () -> boundary.right () - boundary.width (), boundary.rightProperty (), boundary.widthProperty () ) );
    }

    protected void verticalBindings ( Boundary boundary )
    {
        if ( top != null && bottom != null )
        {
            final double l = top.value;
            final double r = bottom.value;

            if ( top.isProportional )
                boundary.topProperty ().bind ( Bindings.createDoubleBinding ( () -> parent.top() + l * parent.height (), parent.topProperty (), parent.heightProperty () ) );
            else
                boundary.topProperty ().bind ( Bindings.createDoubleBinding ( () -> parent.top() + l, parent.topProperty () ) );

            if ( bottom.isProportional )
                boundary.bottomProperty ().bind ( Bindings.createDoubleBinding ( () -> parent.bottom() - r * parent.height (), parent.bottomProperty (), parent.heightProperty () ) );
            else
                boundary.bottomProperty ().bind ( Bindings.createDoubleBinding ( () -> parent.bottom() - r, parent.bottomProperty () ) );

            boundary.heightProperty ().bind ( Bindings.createDoubleBinding ( () -> boundary.bottom () - boundary.top (), boundary.topProperty (), boundary.bottomProperty () ) );

            return;
        }

        if ( top != null && height != null )
        {
            final double l = top.value;
            final double w = height.value;

            if ( top.isProportional )
                boundary.topProperty ().bind ( Bindings.createDoubleBinding ( () -> parent.top() + l * parent.height (), parent.topProperty (), parent.heightProperty () ) );
            else
                boundary.topProperty ().bind ( Bindings.createDoubleBinding ( () -> parent.top() + l, parent.topProperty () ) );

            if ( height.isProportional )
                boundary.heightProperty ().bind ( Bindings.createDoubleBinding ( () -> w * parent.height(), parent.heightProperty () ) );
            else
                boundary.heightProperty ().set ( w );

            boundary.bottomProperty ().bind ( Bindings.createDoubleBinding ( () -> boundary.top () + boundary.height (), boundary.topProperty (), boundary.heightProperty () ) );
            
            return;
        }

        // Bottom and Height

        final double r = bottom.value;
        final double w = height.value;

        if ( bottom.isProportional )
            boundary.bottomProperty ().bind ( Bindings.createDoubleBinding ( () -> parent.bottom() - r * parent.height (), parent.bottomProperty (), parent.heightProperty () ) );
        else
            boundary.bottomProperty ().bind ( Bindings.createDoubleBinding ( () -> parent.bottom() - r, parent.bottomProperty () ) );

        if ( height.isProportional )
            boundary.heightProperty ().bind ( Bindings.createDoubleBinding ( () -> w * parent.height(), parent.heightProperty () ) );
        else
            boundary.heightProperty ().set ( w );

        boundary.topProperty ().bind ( Bindings.createDoubleBinding ( () -> boundary.bottom () - boundary.height (), boundary.bottomProperty (), boundary.heightProperty () ) );
    }
}
