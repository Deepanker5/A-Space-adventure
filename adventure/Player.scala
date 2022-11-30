package o1.adventure

import scala.collection.mutable.Map

/** A `Player` object represents a player character controlled by the real-life user
  * of the program.
  *
  * A player object’s state is mutable: the player’s location and possessions can change,
  * for instance.
  *
  * @param startingArea  the player’s initial location */
class Player(startingArea: Area):

  private var currentLocation = startingArea        // gatherer: changes in relation to the previous location
  private var quitCommandGiven = false              // one-way flag
  private val storage = Map[String, Item]()
  private var repair_status = false
  /** Determines if the player has indicated a desire to quit the game. */
  def hasQuit = this.quitCommandGiven

  /** Returns the player’s current location. */
  def location = this.currentLocation


  /** Attempts to move the player in the given direction. This is successful if there
    * is an exit from the player’s current location towards the direction name. Returns
    * a description of the result: "You go DIRECTION." or "You can't go DIRECTION." */
  def go(direction: String) =
    val destination = this.location.neighbor(direction)
    this.currentLocation = destination.getOrElse(this.currentLocation)
    if destination.isDefined then "You go " + direction + "." else "You can't go " + direction + "."


  /** Causes the player to rest for a short while (this has no substantial effect in game terms).
    * Returns a description of what happened. */
  def rest() =
    "You rest for a while. Better get a move on, though."


  /** Signals that the player wants to quit the game. Returns a description of what happened within
    * the game as a result (which is the empty string, in this case). */
  def quit() =
    this.quitCommandGiven = true
    ""
  def drop(itemName: String): String =
    var public_storage = storage.get(itemName)
    storage-=itemName
    var temp = ""
    var counter = 0
    public_storage match
      case Some(t) => counter = 1
      case None => counter = 0
    if counter == 1 then
      var temp2 =public_storage.get
      currentLocation.addItem(temp2)
      temp = s"You drop the ${itemName}."
    else
      temp = "You don't have that"
    temp

  def has(itemName: String): Boolean =
    var temp2 = storage.get(itemName)
    var counter = 0
    temp2 match
      case Some(another) => counter = 1
      case None => counter = 2
    if counter == 1 then
      true
    else
      false

  def get(itemName: String): String =
    var temp = currentLocation.contains(itemName)
    var count = 0
    var temp2 = ""
    var pp = 0

    if !temp then
      temp2 = s"There is no ${itemName} here to pick up."
    else
      var multiple = currentLocation.removeItem(itemName)
      storage += multiple.get.name -> multiple.get
      temp2 = s"You pick up the ${itemName}."
    temp2

  def examine(itemName: String): String =
    var public_storage = storage.get(itemName)
    var temp = ""
    var counter = 0
    public_storage match
      case Some(value) => counter = 1
      case None => counter = 0
    if counter != 1 then
      temp = s"If you want to examine something, you need to pick it up first."
    else
      temp = s"You look closely at the ${itemName}.\n ${public_storage.get.description}"
    temp

  def inventory: String =
    var temp = storage.keys.mkString("\n")
    var key_size = storage.keySet.size
    var temp2 = ""
    if key_size == 0 then
      temp2 = "You are empty-handed."
    else
      temp2 = s"You are carrying:" + s"\n${temp}"
    temp2
  def repairStatus = this.repair_status
  def repair:String=
    if this.storage.contains("powercell") && this.storage.contains("antenna") then
      if this.location.name == "NoseCone" then
        this.repair_status=true
        "Repair attempted\n...\n...\n...\nSuccessful!!"
      else
        "You cannot repair here, go to the NoseCone!"


    else "you dont have all of the items"
  /** Returns a brief description of the player’s state, for debugging purposes. */
  override def toString = "Now at: " + this.location.name

  def use(itemname:String) :String =
    this.storage.get(itemname) match
      case Some(x) => x.use()
      case None => s"You have no $itemname that can be used"

  def help(command:String) =
    command match
      case "go"        => "go _: Attempts to move the player in the given direction."
      case "rest"      => "rest: Causes the player to rest for a short while (this has no substantial effect in game terms)."
      case "quit"      => "quit: Signals that the player wants to quit the game."
      case "get"       => "get _: Picks up the given item if available in the area."
      case "examine"   => "examine _: Examines item if possible"
      case "drop"      => "drop _: Drops item from self inventory to area if possible"
      case "inventory" => "inventory: Lists items in inventory"
      case "repair"     => "repair: Repairs the spaceship, only possible when in possesion of Powercell and Antenna "
      case "use"       => "use _: Uses the given Item"
      case ""          => "Available commands: go,rest,quit,get,examine.drop,inventory,repair,use.\n Type help with command for more information\n Ex. help go "
      case other       => "That command is not available"





end Player

