!main.



+!main <-
    generic/print( "initial plan" );

    NumberData = 1;
    StringData = "FooBar";

    message/send( "Default 0", NumberData, StringData );
    message/broadcast( "regular expression of agent names", NumberData, StringData )
.    


+!message/receive( message(M), from(F) ) <-
    generic/print( "receive message", M, "from", F )
.    
