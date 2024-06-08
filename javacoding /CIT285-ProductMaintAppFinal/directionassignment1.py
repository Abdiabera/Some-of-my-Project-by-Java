"""
###################
#Abdi Abera               #
#Professor Cerami    #
#CSC:287                 #
##################
#####################################################################################
Text-Based Adventure Game
Next, let’s create a simple text-based adventure game.

There is no real goal of this game, except that you will enable the user to explore a world of your own making.  Users will explore your world by entering:  east, west, north, south, up and down.  Users can also type ‘look’ to see a description of their current location and type ‘quit’ to end the game.

To receive full credit:

You must create at least 6 locations in your world.  Please be creative!
You must break your program into functions, and include comments.

Hint:  Connecting the locations via some kind of internal map is the tricky part of this program.  Consider what data structures might be best for this.

Below is a sample run from my world.  User input is denoted in bold.

You are standing in a large garden.
You can see beautiful flowers all around.
There is a small house to the east.
> east
You are standing in a kitchen.
It's a mess in here!
You can see a garden to the west, and a small bedroom to the east.
> west
You are in the Garden.
> look
You are standing in a large garden.
You can see beautiful flowers all around.
There is a small house to the east.
You are in the Garden.
> east
You are in the Kitchen.
> look
You are standing in a kitchen.
It's a mess in here!
You can see a garden to the west, and a small bedroom to the east.
You are in the Kitchen.
> east
You are standing in a small bedroom.
There is a small hole in the ground and,
it looks like you can climb down.
You can also see a kitchen to the west.
> down
You are standing in a room that looks like a hidden, nuclear bunker.
There are metal canisters along the wall.
There is a ladder going up.
It's a bit dark, but you can also see a panel of buttons to the left.
> quit
Thanks for playing!
##########################################################################
"""



def get_location(val): #used to retrn the key input
    for key, value in Map.items():
         if val == value:
             return key
 
    #used for debugging

Map = { #is a dictionary to store
    'The room ':[0, 0, 0], 'train station':[0, 1, 0],
    'school':[-1, 0, 0], 'bus station':[-1, -1, 0], 'garden of life':[0, 2, 0],
    'church of God':[1, 1, 0],'river of Abay':[1, 0, -1], 'mountain':[-1, 0, 1],
    }

def main():
   
    x = "" #used to intialize loop
    LTeller = [0, 0, 0] # is used to call  Map (x, y, z)
    LLooker = [] #is used to the look input
   

    while(x != "quit"):# forever while loop
        print("You are standing in the " + get_location(LTeller) + ".")#tused to get current location after being altered
        x = input() # user input

        #These are  6 inputs
        if(x == "north"):#is used to dictates which elements are going to change in lTeller
            LTeller[1] += 1 #this is to alter the particular element in the list
            if(get_location(LTeller) == "key doesn't exist"):#this is to make sure that location exists
                print("Error,  commond doesn't exist.")
                LTeller[1] -= 1#this is to reset the location if the location didn't exist

        if(x == "east"):
            LTeller[0] += 1
            if(get_location(LTeller) == "key doesn't exist"):
                print("Error,  commond doesn't exist")
                LTeller[0] -= 1
       
        if(x == "south"):
            LTeller[1] -= 1
            if(get_location(LTeller) == "key doesn't exist"):
                print("Error,  commond doesn't exist.")
                LTeller[1] += 1
       
        if(x == "west"):
            LTeller[0] -= 1
            if(get_location(LTeller) == "key doesn't exist"):
                print("Error,  commond doesn't exist.")
                LTeller[0] += 1
       
        if(x == "up"):
            LTeller[2] += 1
            if(get_location(LTeller) == "key doesn't exist"):
                print("Error,  commond doesn't exist.")
                LTeller[2] -= 1
       
        if(x == "down"):
            LTeller[2] -= 1
            if(get_location(LTeller) == "key doesn't exist"):
                print("Error,  commond doesn't exist.")
                LTeller[2] += 1

        if(x == "look"): #this checks all 6 directions and printing if something exists
            LLooker = LTeller.copy() #this is for holding a copy of the list so we can alter
            LLooker[1] += 1
            if((LLooker) in Map.values() != False): #to check if that direction exists
                print(get_location(LLooker) + " is to the north.")

            LLooker = LTeller.copy() #it copy the location to reset
            LLooker[0] += 1
            if((LLooker) in Map.values() != False):
                print(get_location(LLooker) + " is to the east.")

            LLooker = LTeller.copy()
            LLooker[1] -= 1
            if((LLooker) in Map.values() != False):
                print(get_location(LLooker) + " is to the south.")

            LLooker = LTeller.copy()
            LLooker[0] -= 1
            if((LLooker) in Map.values() != False):
                print(get_location(LLooker) + " is to the west.")

            LLooker = LTeller.copy()
            LLooker[2] += 1
            if((LLooker) in Map.values() != False):
                print(get_location(LLooker) + " is to the up.")

            LLooker = LTeller.copy()
            LLooker[2] -= 1
            if((LLooker) in Map.values() != False):
                print(get_location(LLooker) + " is to the down.")

        #the following are descriptions if you land on that location
        if(get_location(LTeller) == 'train station'):
            print("It's very wet here.")

        if(get_location(LTeller) == 'garden of life'):
            print("It smells very nice here.")

        if(get_location(LTeller) == 'church of God'):
            print("You feel very holy here and you can have faith to true God.")

        if(get_location(LTeller) == 'school'):
            print("There are a lot of student.")

        if(get_location(LTeller) == 'hospital'):
            print("There's a lot of house .")

        if(get_location(LTeller) == 'river of Abay'):
            print("It's extra wet here and feels cold.")

        if(get_location(LTeller) == 'mountain'):
            print("walking is getting harder.")

       
    print("Thank you for playing!")

           

if __name__ == "__main__":
    main()