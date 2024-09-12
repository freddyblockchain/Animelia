package com.mygdx.game

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Circle
import com.badlogic.gdx.math.MathUtils.cos
import com.badlogic.gdx.math.MathUtils.sin
import com.badlogic.gdx.math.Vector2
import com.mygdx.game.Ability.ELEMENTAL_TYPE
import com.mygdx.game.Animelia.anivolutionCheck
import com.mygdx.game.Area.Area
import com.mygdx.game.Area.AreaType
import com.mygdx.game.Area.getAreaType
import com.mygdx.game.GameObjects.GameObject.GameObject
import com.mygdx.game.GameObjects.Ground
import com.mygdx.game.GameObjects.MoveableEntities.Characters.PlayerEnvironmentState
import com.mygdx.game.GameObjects.Other.Wall
import com.mygdx.game.Managers.AreaManager
import com.mygdx.game.Managers.SignalManager
import kotlin.math.PI

fun InitArea(levelName: String){
    val levelPath = "assets/levels/${levelName}/data.json"
    val root = JsonParser.getRoot(levelPath)
    val correspondingArea = AreaManager.areas.firstOrNull { it.areaIdentifier == root.customFields.World }
        ?:
        Area(root.customFields.World)
    if(!(AreaManager.areas.any { it.areaIdentifier == correspondingArea.areaIdentifier })){
        AreaManager.areas.add(correspondingArea)
    }
    correspondingArea.associatedLevels.add(levelName)
    AreaManager.uniqueIdToLevelPathMap[root.uniqueIdentifer] = levelName
    AreaManager.levelToAreaMap[levelName] = correspondingArea.areaIdentifier

}

fun getObjectsFromLevelName(levelName: String): List<GameObject>{
    val levelPath = "assets/levels/${levelName}/data.json"
    val root = JsonParser.getRoot(levelPath)
    val entityObjects = JsonParser.getGameObjects(root)
    val ground = Ground(GameObjectData(x = root.x, y = (-root.y) - root.height), Vector2(root.width.toFloat(), root.height.toFloat()), "levels/${levelName}/_composite.png")
    val wall = Wall(GameObjectData(), Vector2(0f,0f), ground)
    val objectsToReturn = entityObjects + ground + wall
    objectsToReturn.forEach { it.areaIdentifier = AreaManager.levelToAreaMap[levelName]!! }

    return objectsToReturn
}

fun addObjectsToArea(area: Area, objectsToAdd: List<GameObject>){
    area.gameObjects.addAll(objectsToAdd)
    area.gameObjects.forEach {
        it.areaIdentifier = area.areaIdentifier
    }
}

fun changeArea(newPos: Vector2, newAreaIdentifier: String, shouldSave: Boolean = true){
    player.activeAbilities.values.forEach { it?.onDeactivate() }
    val newPos = Vector2(newPos.x, newPos.y)

    AreaManager.changeActiveArea(newAreaIdentifier)

    player.setPosition(newPos)
    player.startingPosition = newPos
    anivolutionCheck()
    if(shouldSave){
        //updateAndSavePlayer()
    }

    val pastSignalsInArea = SignalManager.pastSignals.filter { it.areaIdentifer == newAreaIdentifier }
    SignalManager.signalManager.addAll(pastSignalsInArea)

    if(getAreaType(newAreaIdentifier) == AreaType.Ice && (ELEMENTAL_TYPE.ICE !in player.animeliaInfo.elemental_types)){
        player.playerEnvironmentState = PlayerEnvironmentState.COLD
    }else if(getAreaType(newAreaIdentifier) == AreaType.Fire && (ELEMENTAL_TYPE.FIRE !in player.animeliaInfo.elemental_types)){
        player.playerEnvironmentState = PlayerEnvironmentState.HOT
    } else{
        player.playerEnvironmentState = PlayerEnvironmentState.NORMAL
    }
}

fun initAreas(){
    val directoryPath = "assets/levels/"  // Path inside the assets directory
    val filesHandle = Gdx.files.internal(directoryPath)
    if (filesHandle.exists() && filesHandle.isDirectory) {
        filesHandle.list().forEach { level ->
            InitArea(level.name())
        }
    }

}

fun getUnitVectorTowardsPoint(position: Vector2, point: Vector2): Vector2 {
    return Vector2(point).sub(position).nor()
}

fun renderRepeatedTexture(batch: SpriteBatch, texture: Texture, position: Vector2, size: Vector2) {
    texture.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat)
    batch.draw(texture, position.x, position.y, 0, 0, size.x.toInt(), size.y.toInt())
}

fun InsideCircle(circleObject: GameObject, circleRadius: Float, targetObject: GameObject): Boolean{
    val circleToCheck = Circle(
        circleObject.sprite.x,
        circleObject.sprite.y,
        circleRadius
    )
    return circleToCheck.contains(targetObject.currentPosition())
}

fun getRotatedUnitVectorClockwise(unitVector: Vector2, angleDegrees: Float): Vector2 {
    val x = unitVector.x
    val y = unitVector.y
    val radians = (angleDegrees * PI / 180).toFloat()  // Convert angle from degrees to radians
    val newX = x * cos(radians) + y * sin(radians)
    val newY = -x * sin(radians) + y * cos(radians)
    return Vector2(newX, newY)
}
/*

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.math.Intersector.intersectSegments
import com.badlogic.gdx.math.Polygon
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.FloatArray
import com.mygdx.game.AbstractClasses.*
import com.mygdx.game.Collitions.DoorCollition
import com.mygdx.game.DataClasses.DoorData
import com.mygdx.game.Enums.Direction
import com.mygdx.game.Enums.Element
import com.mygdx.game.Enums.ItemType
import com.mygdx.game.GameObjects.MoveableEntities.Characters.Player
import com.mygdx.game.GameObjects.Other.Door
import com.mygdx.game.GameObjects.MoveableEntities.IceClone
import com.mygdx.game.Interfaces.*
import com.mygdx.game.Locations.DefaultLocation
import com.mygdx.game.Locations.DefaultLocationData
import com.mygdx.game.Managers.AreaManager
import com.mygdx.game.Managers.LocationManager
import com.mygdx.game.Managers.SignalManager
import com.mygdx.game.ObjectProperties.Fire
import com.mygdx.game.ObjectProperties.Ice
import com.mygdx.game.ObjectProperties.ROJParticleObject
import com.mygdx.game.Saving.PlayerSaveState
import com.mygdx.game.Saving.SaveStateEntity
import com.mygdx.game.Signal.Signals.ItemPickedUpSignal
import com.mygdx.game.Signal.Signals.RemoveObjectSignal
import com.mygdx.game.Utils.RectanglePolygon
import kotlin.math.*
import kotlin.random.Random


var font: BitmapFont = BitmapFont()

fun distance(point1: Vector2, point2: Vector2): Float {
    val first = (point2.x - point1.x).pow(2) + (point2.y - point1.y).pow(2)
    return sqrt(first)
}

fun getPolygonPoints(polygon: Polygon): List<Vector2> {
    val floatArray = polygon.transformedVertices
    val xValues = floatArray.filterIndexed { index, _ -> index.toFloat() % 2f == 0f }
    val yValues = floatArray.filterIndexed { index, _ -> index % 2f == 1f }
    val listOfVectors = xValues.zip(yValues).map { (xvalue, yvalue) -> Vector2(xvalue, yvalue) }
    return listOfVectors
}

fun intersectPolygonEdges(polygon1: FloatArray, polygon2: FloatArray): Boolean {
    val last1 = polygon1.size - 2
    val last2 = polygon2.size - 2
    val p1 = polygon1.items
    val p2 = polygon2.items
    var x1 = p1[last1]
    var y1 = p1[last1 + 1]
    var i = 0
    while (i <= last1) {
        val x2 = p1[i]
        val y2 = p1[i + 1]
        var x3 = p2[last2]
        var y3 = p2[last2 + 1]
        var j = 0
        while (j <= last2) {
            val x4 = p2[j]
            val y4 = p2[j + 1]
            if (intersectSegments(x1, y1, x2, y2, x3, y3, x4, y4, null)) return true
            x3 = x4
            y3 = y4
            j += 2
        }
        x1 = x2
        y1 = y2
        i += 2
    }
    return false
}

fun getUnitVectorTowardsPoint(position: Vector2, point: Vector2): Vector2 {
    return Vector2(point).sub(position).nor()
}

fun getOppositeUnitVector(position: Vector2, point: Vector2): Vector2 {
    val unitvector = getUnitVectorTowardsPoint(position, point)
    return Vector2(-unitvector.x, -unitvector.y)
}

fun unitVectorToAngle(unitVector: Vector2): Float {
    return (atan2(unitVector.y, unitVector.x) * 180 / PI).toFloat()
}


enum class InsertDirection { LEFT, UP, RIGHT, DOWN, MIDDLE }

fun GetPositionRelativeToLocation(
    defaultLocation: DefaultLocation,
    size: Vector2,
    direction: InsertDirection,
    directionOnPlane: InsertDirection,
    modifier: Vector2 = Vector2(0f, 0f)
): Vector2 {

    val DirectionOnPlane = when (direction) {
        InsertDirection.LEFT, InsertDirection.RIGHT ->
            when (directionOnPlane) {
                InsertDirection.UP -> (defaultLocation.topleft.y - defaultLocation.bottomleft.y) - size.y
                InsertDirection.DOWN -> 0f
                else -> ((defaultLocation.topleft.y - defaultLocation.bottomleft.y) / 2f - size.y / 2)
            }
        InsertDirection.UP, InsertDirection.DOWN ->
            when (directionOnPlane) {
                InsertDirection.RIGHT -> (defaultLocation.topright.x - defaultLocation.topleft.x) - size.x
                InsertDirection.LEFT -> 0f
                else -> (defaultLocation.topright.x - defaultLocation.topleft.x) / 2f - size.x / 2
            }
        else -> 0f
    }

    return when (direction) {
        InsertDirection.LEFT -> Vector2(
            defaultLocation.bottomleft.x - size.x,
            defaultLocation.bottomleft.y + DirectionOnPlane
        )
        InsertDirection.UP -> Vector2(
            defaultLocation.topleft.x + DirectionOnPlane,
            defaultLocation.topleft.y
        )
        InsertDirection.RIGHT -> Vector2(
            defaultLocation.bottomright.x,
            defaultLocation.bottomleft.y + DirectionOnPlane
        )
        InsertDirection.DOWN -> Vector2(
            defaultLocation.topleft.x + DirectionOnPlane,
            defaultLocation.bottomleft.y - size.y
        )
        else -> Vector2(0f, 0f)
    } + modifier
}

fun addLocation(defaultLocation: DefaultLocation, area: Area): DefaultLocation {
    area.addLocation(defaultLocation)
    return defaultLocation
}

fun addLocationRelative(
    defaultLocation: DefaultLocation,
    size: Vector2,
    direction: InsertDirection,
    area: Area,
    directionOnPlane: InsertDirection,
    objectCreationMethod: () -> List<GameObject> = { listOf() },
    locationDataStrategy: LocationDataStrategy = DefaultLocationData(),
    positionModifier: Vector2 = Vector2(0f, 0f)
): DefaultLocation {
    val pos1 = GetPositionRelativeToLocation(defaultLocation, size, direction, directionOnPlane, positionModifier)
    val newLocation = DefaultLocation(size, pos1, objectCreationMethod, locationDataStrategy)
    defaultLocation.addAdjacentLocation(newLocation)
    newLocation.addAdjacentLocation(defaultLocation)
    return addLocation(newLocation, area)
}

fun addLocationsToArea(area: Area) {
    area.defaultLocations.forEach { x -> x.initLocation() }
}

fun handleMoveCollitions(gameObject: GameObject, polygonToCheck: Polygon, objectsToCheck: List<GameObject>): Boolean {
    val collidingObjects = GetCollidingObjects(gameObject, polygonToCheck, objectsToCheck - gameObject)
    val collitions: List<MoveCollition> = collidingObjects.map { x -> x.collition as MoveCollition }
    collidingObjects.forEach {
        it.collition.collitionHappened(gameObject)
        gameObject.collition.collitionHappened(it)
    }
    // Handle moving object away from previous colliding object.
    handleAreaExitCollitions(gameObject,collidingObjects)
    gameObject.collidingObjects = collidingObjects


    return collitions.all { x -> x.canMoveAfterCollition }
}

fun handleKeyCollitions(objectsToCheck: List<GameObject>) {
    val collidingObjects = GetCollidingObjects(player, player.polygon,objectsToCheck)
    collidingObjects.forEach { x -> x.collition.collitionHappened(player); }
}

fun handleKeyPressable(objectsToCheck: List<GameObject>) {
    val collidingObjects = GetCollidingObjects(player, player.polygon,objectsToCheck)
    collidingObjects.forEach { (it.collition as KeyPressedCollition).renderKeyToUI(player, it) }
}

fun GameObject.rotate(rotationDegree: Float) {
    this.sprite.rotate(rotationDegree)
    this.polygon.rotate(rotationDegree)
}

fun GameObject.InitSprite(texture: Texture): Sprite {
    val sprite = Sprite(texture)
    sprite.setSize(size.x, size.y)
    sprite.setOriginCenter()
    sprite.setPosition(initPosition.x, initPosition.y)
    return sprite
}

fun InitPolygon(sprite: Sprite): Polygon {
    val polygon = RectanglePolygon(sprite.boundingRectangle)
    polygon.setOrigin(sprite.x + sprite.originX, sprite.y + sprite.originY)
    polygon.setPosition(sprite.x - polygon.vertices[0], sprite.y - polygon.vertices[1])
    return polygon
}

inline fun ConstructObjects(
    gameobjectFactory: (Position: Vector2, Size: Vector2, defaultLocation: DefaultLocation) -> GameObject,
    fromx: Int,
    incrementx: Int,
    endx: Int,
    fromy: Int,
    incrementy: Int,
    endy: Int,
    defaultLocation: DefaultLocation
): List<GameObject> {
    val objects = mutableListOf<GameObject>()
    for (y in fromy downTo endy step incrementy) {
        for (x in fromx..endx step incrementx) {
            val gameObject = gameobjectFactory(
                Vector2(x.toFloat(), y.toFloat()),
                Vector2(incrementx.toFloat(), incrementy.toFloat()),
                defaultLocation
            )
            objects.add(gameObject)
        }
    }
    return objects.toList()
}

fun middleOfObject(Position: Vector2, size: Vector2): Vector2 {
    return Vector2(Position.x - (size.x / 2), Position.y - (size.y / 2))
}

fun renderRepeatedTexture(batch: PolygonSpriteBatch, texture: Texture, position: Vector2, size: Vector2) {
    texture.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat)
    batch.draw(texture, position.x, position.y, 0, 0, size.x.toInt(), size.y.toInt())
}

fun checkOpposingDirections(player: Player, directionalObject: DirectionalObject): Boolean {
    return when (player.direction) {
        Direction.UP -> directionalObject.direction == Direction.DOWN
        Direction.LEFT -> directionalObject.direction == Direction.RIGHT
        Direction.RIGHT -> directionalObject.direction == Direction.LEFT
        Direction.DOWN -> directionalObject.direction == Direction.UP
    }
}

fun GetCollidingObjects(gameObjectToCheck: GameObject, polygonToCheck: Polygon, gameObjects: List<GameObject>): List<GameObject> {
    val collidingObjects = gameObjects.filter {gameObjectToCheck.collitionMask.canCollideWith(it) && it.collitionMask.canCollideWith(gameObjectToCheck) && isPolygonsColliding(polygonToCheck, it.polygon) }
    val filteredCollitions = collidingObjects.fold(
        collidingObjects
    ) { objects, nextObject -> nextObject.collition.filterCollitions(objects) }
    return filteredCollitions
}

fun isPolygonsColliding(polygon1: Polygon, polygon2: Polygon): Boolean {
    return intersectPolygonEdges(FloatArray(polygon1.transformedVertices), FloatArray(polygon2.transformedVertices))
            || polygon1.anyPointInPolygon(polygon2)
}

fun InitAssets(): AssetManager {
    val assetManager = AssetManager()
    return assetManager
}

fun entityWithinLocations(polygonToCheck: Polygon): Boolean {
    var inLocation1 = false
    for (point in getPolygonPoints(polygonToCheck)) {
        inLocation1 = false
        for (rectangle in LocationManager.activeDefaultLocations.map { x -> x.sprite.boundingRectangle }) {
            if (rectangle.contains(point)) {
                inLocation1 = true
                break
            }
        }
        if (!inLocation1) {
            break
        }
    }
    return inLocation1
}

fun getDirectionFromAngle(angleToCheck: Float): Direction {

    return when (angleToCheck) {
        in -45f..45f -> Direction.DOWN
        in 45f..135f -> Direction.RIGHT
        in 135f..225f -> Direction.UP
        else -> Direction.LEFT
    }
}

fun getGameObjectWithEntityId(entityId: Int): GameObject? {
    val relevantObjects: List<GameObject> = AreaManager.getAllGameObjects().filter { it is SaveStateEntity }
    val matchingObject: GameObject? = relevantObjects.find { (it as SaveStateEntity).entityId == entityId }
    return matchingObject
}


fun itemObjectAddToInventory(itemType: ItemType, itemObject: GameObject) {
    SignalManager.emitSignal(ItemPickedUpSignal(itemType))
    val id = if (itemObject is SaveStateEntity) itemObject.entityId else NOTSAVEDID
    SignalManager.emitSignal(RemoveObjectSignal(id))

}

fun GameObject.getOppositeDirection(
    other: GameObject,
): Vector2 {
    val character = this as DefaultCharacter
    val centerPointObject =
        Vector2(other.sprite.x + other.sprite.width / 2, other.sprite.y + other.sprite.height / 2)
    val centerPointPlayer =
        Vector2(character.sprite.x + character.sprite.width / 2, character.sprite.y + character.sprite.height / 2)
    val oppositeDirection = getOppositeUnitVector(centerPointPlayer, centerPointObject)
    return oppositeDirection
}

fun ResetPlayer(playerSaveState: PlayerSaveState) {
    ResetAreaEnteredCollitions()
    player.setPosition(Vector2(playerSaveState.playerXPos, playerSaveState.playerYPos))
    player.health = player.maxHealth
}

fun Radians(float: Float): Float {
    return (float * (PI / 180f)).toFloat()
}

fun generateEnemyProjectile(
    projectileFactory: (Position: Vector2, Size: Vector2, defaultLocation: DefaultLocation, unitVectorDirection: Vector2, shooter: GameObject) -> Projectile,
    enemy: Enemy,
    size: Vector2
) {
    val cloneOrPlayer = getCloneOrPlayer()
    val unitVector = getUnitVectorTowardsPoint(Vector2(enemy.sprite.x, enemy.sprite.y), Vector2(cloneOrPlayer.sprite.x, cloneOrPlayer.sprite.y))
    val enemyStart = enemy.currentMiddle
    enemy.defaultLocation!!.addGameObject(
        projectileFactory(
            enemyStart + (unitVector * 20f) - Vector2(
                size.x / 2,
                size.y / 2
            ), size, enemy.defaultLocation!!, unitVector, enemy
        )
    )
}

fun getCloneOrPlayer(): GameObject{
    var result: GameObject = player
    val random = Random.nextInt(2)
    val clone = LocationManager.ActiveGameObjects.firstOrNull(){ it is IceClone }
    if (random == 1 && clone != null) {
        result = clone
    }
    return result
}

fun createDoor(doorData: DoorData): Door {
    val locationFrom = LocationManager.findLocation(doorData.locationFrom, doorData.areaFrom)

    val doorConnection = doorConnectionMap.getOrPut(doorData.connectionKey, { DoorConnection() })

    val doorCollitionFrom = DoorCollition(
        doorData.position,
        doorData.areaTo, doorConnection,
        doorData.direction
    )
    val doorFrom = Door(
        doorData.position, doorData.size, DefaultTextureHandler.getTexture(doorData.textureName), locationFrom,
        doorData.direction, doorCollitionFrom
    )
    return doorFrom
}

fun setAggroIfShouldBeAggroed(shouldBeAggroedStrategy: ShouldBeAggroedStrategy, aggroableEntity: AggroableEntity) {
    if (shouldBeAggroedStrategy.ShouldBeAggroed()) {
        aggroableEntity.setAggroed()
    }
}

fun handleAreaExitCollitions(gameObject: GameObject,collidingObjects: List<GameObject>){
    val oldCollitions = gameObject.collidingObjects.minus(collidingObjects.toSet())
    if(oldCollitions.isNotEmpty()){
        oldCollitions.forEach {
            val collition = it.collition
            handleAreaExitCheckAndAction(it.collition, gameObject)
            handleAreaExitCheckAndAction(gameObject.collition, it)
        }

    }
}

fun handleAreaExitCheckAndAction(collition: Collision, objectLeaved: GameObject){
    if(collition is AreaEntranceCollition){
        if(collition.insideCollition.getOrDefault(objectLeaved, true)){
            collition.movedOutside(objectLeaved)
        }
    }
}
fun MoveableObject.circularMove(radius: Float, prevAngle: Float, angle: Float) {
    val currentPos = Vector2(radius * cos(Radians(prevAngle)), radius * sin(Radians(prevAngle)))
    val newPos = Vector2(radius * cos(Radians(angle)), radius * sin(Radians(angle)))
    this.unitVectorDirection = (newPos - currentPos)
    this.move(this.unitVectorDirection)
}

fun getPropertyBasedOnElement(element: Element, gameObject: GameObject): ROJParticleObject?{
    return when(element) {
        Element.FIRE -> Fire(gameObject)
        Element.ICE -> Ice(gameObject)
        Element.EARTH -> null
    }
}
fun updateMap(area: Area){

    val locations = area.defaultLocations

    val polygons = locations.map {
        val newVertices = it.polygon.transformedVertices.map { vertice -> vertice / 10 }
        Polygon(newVertices.toFloatArray())
    }

    val cumuPos: Vector2 = polygons.fold(Vector2(0f,0f)) { vec, pol ->
        vec + Vector2(pol.vertices[0], pol.vertices[1])
    }
    val middlePos = cumuPos / polygons.size.toFloat()

    Map.offset = middlePos

    Map.currentMap = polygons.zip(locations).toMutableList()
}

fun ResetAreaEnteredCollitions() {
    LocationManager.ActiveGameObjects.forEach {
        it.onLocationEnterActions.forEach { it() }
        val collition = it.collition
        if (collition is AreaEntranceCollition) {
            collition.insideCollition.forEach {pair ->
                collition.movedOutside(pair.key)
            }
        }
    }
}
*/