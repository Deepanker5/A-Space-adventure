package o1.adventure

import o1.adventure.ui.{AdventureGUI, AdventureTextUI}
import scala.collection.mutable.Buffer

/** The class `Item` represents items in a text adventure game. Each item has a name
  * and a longer description. (In later versions of the adventure game, items may
  * have other features as well.)
  *
  * N.B. It is assumed, but not enforced by this class, that items have unique names.
  * That is, no two items in a game world have the same name.
  *
  * @param name         the item’s name
  * @param description  the item’s description */
class Item(val name: String, val description: String):

  /** Returns a short textual representation of the item (its name, that is). */
  override def toString = this.name
  def use(): String = "You cannot use this item."
end Item

class Sensor(loca: Buffer[Area]) extends Item("sensor","Its a Quantum Sensor, capable of locating necesary items.\nWith this fixing the ship should be easier!\nWhat a find!"):


  var powercell = true
  var antenna = true



  override def use() =
    val tempp = Buffer[Area]()

    if loca(0).contains("powercell") then
      tempp.append(loca(0))
    else
      powercell = false
    if loca(1).contains("antenna") then
      tempp.append(loca(1))
    else
      antenna = false


    if powercell && antenna then
      s"Items powercell and antenna found in ${tempp.head.name} and ${tempp(1).name}"
    else if powercell then
      s"Item powercell can be in ${tempp.head.name}!"
    else if antenna then
      s"Item powercell can be in ${tempp.head.name}!"
    else
      s"There are no more items to find!"















