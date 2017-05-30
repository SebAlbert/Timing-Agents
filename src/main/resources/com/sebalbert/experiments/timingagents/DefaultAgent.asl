!main.



+!main <-
    generic/print( "initial plan" );

    !test( 0 );

    NumberData = 1;
    StringData = "FooBar";

    message/send( "Default 0", NumberData, StringData );
    message/broadcast( "regular expression of agent names", NumberData, StringData )

.


+!message/receive( message(M), from(F) ) <-
    generic/print( "receive message", M, "from", F )
.

+!test( X ) <-
    generic/print( "test", X );
    D = simtime/current();
    generic/print( D );
    DT = datetime/create( D );
    generic/print( DT );
    N = datetime/applyseconds("plus", 5, DT);
    generic/print( N );
    generic/print ( schedule/length );
    Y = X + 1;
    S = string/concat( "test( ", generic/type/tostring( Y ), ")" );
    schedule/addgoal( N, S );
    generic/print( "added" );
    generic/print ( schedule/length )
.

+!simtime/advance( T ) <-
    generic/print( "Agent detected advance of time by ", T );
    schedule/consume
.
