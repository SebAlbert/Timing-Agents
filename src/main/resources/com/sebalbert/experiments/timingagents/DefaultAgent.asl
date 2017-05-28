!main.



+!main <-
    generic/print( "initial plan" );

    !test;

    NumberData = 1;
    StringData = "FooBar";

    message/send( "Default 0", NumberData, StringData );
    message/broadcast( "regular expression of agent names", NumberData, StringData )

.


+!message/receive( message(M), from(F) ) <-
    generic/print( "receive message", M, "from", F )
.

+!test <-
    generic/print( "test" );
    D = simtime/current();
    generic/print( D );
    DT = datetime/create( D );
    generic/print( DT );
    N = datetime/applyseconds("plus", 5, DT);
    generic/print( N );
    generic/print ( schedule/length );
    schedule/addgoal( N, "test" );
    generic/print( "added" );
    generic/print ( schedule/length )
.
