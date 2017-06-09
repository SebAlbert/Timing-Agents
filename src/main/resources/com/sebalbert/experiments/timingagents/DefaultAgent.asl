!main.



+!main <-
    generic/print( "initial plan" );

    !test( 0 );
    //!countdown( 3 );

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
    DT = simtime/current();
    generic/print( DT );
    N = datetime/applyseconds("plus", 5, DT);
    generic/print( N );
    Y = X + 1;
    L = generic/type/parseliteral( string/concat( "test( ", generic/type/tostring( Y ), ")" ) );
    +nextthing( L );
    nextactivation/set( N )
.

+!countdown( N )
: N > 0 <-
    generic/print( N );
    M = N - 1;
    !countdown( M )
: N <= 0 <-
    generic/print( "repair" )
.

+!simtime/advance( T ) <-
    generic/print( "Agent detected advance of time by ", T );
    !nextactivation/execute
.

+!nextactivation/execute
: simtime/current() == nextactivation/get() <-
    >>nextthing( Z );
    generic/print( "nextactivation/execute", Z );
    -nextthing( Z );
    !Z
.
