package o1.adventure

import scala.collection.mutable.Buffer

/** The class `Adventure` represents text adventure games. An adventure consists of a player and
  * a number of areas that make up the game world. It provides methods for playing the game one
  * turn at a time and for checking the state of the game.
  *
  * N.B. This version of the class has a lot of “hard-coded” information that pertains to a very
  * specific adventure game that involves a small trip through a twisted forest. All newly created
  * instances of class `Adventure` are identical to each other. To create other kinds of adventure
  * games, you will need to modify or replace the source code of this class. */
class Adventure:

  /** the name of the game */
  val title = "A Space Adventure"

  private val base              = Area("Base", "You are currently at Base, hurry up and try to collect all of the items required to fix the spaceship..\nItems required: Power Cell, Antenna")
  private val nosecone          = Area("NoseCone", "You are now at the nose cone area of the ship.\nThis is where you can operate the spaceship. Pick up all of the items located here")
  private val window            = Area("Window","You are now at the window.\n You can admire the view of the cold and empty space.")
  private val leftEngine        = Area("Left Engine", "Currently you are in the Left Engine. There is empty space up north.\nThe coldness of space surrounds you.\n Pick up all of the objects located here.")
  private val rightEngine       = Area("Right Engine", "Currently you are in the Right Engine. There is empty space up north.\nThe coldness of space surrounds you.\n Pick up all of the objects located here.")
  private val O2                = Area("O2","You are now in 02.\n The right engine is located east of this location.")
  private val reactor           = Area("Reactor","You are now in the Reactor.\n Be careful to not touch anything.")
  private val communications    = Area("Communications","You are now in Communications.\n You can hear the cold howling from space in this room")
  private val storageCompartment= Area("Storage Compartment", "You are now in the storage compartment. Pick up all of the items here.")


  private val exit              = Area("exit", "Oh no! You fell off the space ship, you lost the game.")
  private val destination       = exit

  base     .setNeighbors(Vector(          "north" -> window,        "east" -> rightEngine,    "south" -> storageCompartment,  "west" -> leftEngine     ))
  storageCompartment.setNeighbors(Vector( "north" -> base,          "east" -> O2                                                                       ))
  O2.setNeighbors(Vector(                 "north" -> window,                                  "south" -> rightEngine                                   ))
  window.setNeighbors(Vector(             "north" -> nosecone,                                "south" -> base,                "west" -> reactor        ))
  nosecone   .setNeighbors(Vector(                                                            "south" -> window                                        ))
  reactor     .setNeighbors(Vector(                                 "east" -> window,                                         "west" -> leftEngine     ))
  communications     .setNeighbors(Vector(                                                    "south" -> leftEngine                                    ))
  leftEngine     .setNeighbors(Vector(    "north" -> communications,"east" -> base,           "south" -> rightEngine,         "west" -> exit           ))
  rightEngine     .setNeighbors(Vector(   "north" -> O2,            "east" -> exit,           "south" -> leftEngine,          "west" -> base           ))

  // TODO: Uncomment the two lines below. Improve the code so that it places the items in Nosecone and Rocket2, respectively.
  val locations = Buffer(leftEngine,rightEngine,storageCompartment,nosecone,window)
  val restLocations = Buffer(reactor,storageCompartment)
  val rand =scala.util.Random


  reactor.addItem(Item("powercell", "It's a power cell. Glows a neon blue."))
  storageCompartment.addItem(Item("antenna", "It's the latest SX-2038 Antennas, capable of producing high frequencies.\nWith this communication can be re-established.\nProblem is, ship is out of power"))
  //locations(rand.nextInt(4)).addItem(Sensor(locations))
  base.addItem(Sensor(restLocations))

  /** The character that the player controls in the game. */
  val player = Player(base)

  /** The number of turns that have passed since the start of the game. */
  var turnCount = 0
  /** The maximum number of turns that this adventure game allows before time runs out. */
  val timeLimit = 40


  /** Determines if the adventure is complete, that is, if the player has won. */
  def isComplete = player.repairStatus&&this.player.location==this.base

  /** Determines whether the player has won, lost, or quit, thereby ending the game. */
  def isOver = this.isComplete || this.player.hasQuit || this.turnCount == this.timeLimit || this.player.location==this.exit

  /** Returns a message that is to be displayed to the player at the beginning of the game. */
  def welcomeMessage = "Welcome to the Space exploration Game. The goal of the game is to explore the rooms in the spaceship and pick up the items and fix the ship.\nYou can fix the spaceship only after collecting all of the items and returning to base and putting in the 'repair' command in base.\n\nBetter hurry, becuase if you take too long then you will loose and be careful to not fall of the spacecraft."


  /** Returns a message that is to be displayed to the player at the end of the game. The message
    * will be different depending on whether or not the player has completed their quest. */
  def goodbyeMessage =
    if this.isComplete then
      "You have collected all of the required items and fixed the space ship. Congrats, you have won the game!"
    else if this.turnCount == this.timeLimit then
      "Oh no! Time's up.\nGame over!"
    else if this.player.location==this.exit then
      "Oh no! You fell off the space ship, you lost the game."
    else  // game over due to player quitting
      "Quitter!"


  /** Plays a turn by executing the given in-game command, such as “go west”. Returns a textual
    * report of what happened, or an error message if the command was unknown. In the latter
    * case, no turns elapse. */
  def playTurn(command: String) =
    val action = Action(command)
    val outcomeReport = action.execute(this.player)
    if outcomeReport.isDefined then
      this.turnCount += 1
    outcomeReport.getOrElse(s"Unknown command: \"$command\".")

end Adventure

