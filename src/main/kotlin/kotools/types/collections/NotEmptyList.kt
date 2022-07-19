package kotools.types.collections

import kotools.types.annotations.SinceKotoolsTypes
import kotools.types.number.PositiveInt
import kotools.types.number.StrictlyPositiveInt
import kotools.types.string.NotBlankString

// ---------- Conversions ----------

/**
 * Returns a not empty list containing all the elements of this array, or throws
 * an [IllegalArgumentException] if this array is empty.
 */
@SinceKotoolsTypes("1.3")
@Throws(IllegalArgumentException::class)
public inline fun <reified E> Array<E>.toNotEmptyList(): NotEmptyList<E> =
    toList().toNotEmptyList()

/**
 * Returns a not empty list containing all the elements of this collection, or
 * throws an [IllegalArgumentException] if this collection is empty.
 */
@SinceKotoolsTypes("1.3")
@Throws(IllegalArgumentException::class)
public inline fun <reified E> Collection<E>.toNotEmptyList(): NotEmptyList<E> {
    require(isNotEmpty()) { "Given collection shouldn't be empty." }
    val head: E = first()
    val list: MutableList<E> = mutableListOf()
    for (index in 1 until size) list += elementAt(index)
    val tail: Array<E> = list.toTypedArray()
    return NotEmptyList(head, *tail)
}

/**
 * Returns a not empty list containing all the elements of this array, or
 * returns `null` if this array is empty.
 */
@SinceKotoolsTypes("1.3")
public inline fun <reified E> Array<E>.toNotEmptyListOrNull(): NotEmptyList<E>? =
    toList().toNotEmptyListOrNull()

/**
 * Returns a not empty list containing all the elements of this collection, or
 * returns `null` if this collection is empty.
 */
@SinceKotoolsTypes("1.3")
public inline fun <reified E> Collection<E>.toNotEmptyListOrNull(): NotEmptyList<E>? =
    try {
        toNotEmptyList()
    } catch (_: IllegalArgumentException) {
        null
    }

/**
 * Returns a not empty list containing all the elements of this array, or
 * returns the result of calling the [defaultValue] function if this array is
 * empty.
 */
@SinceKotoolsTypes("1.3")
public inline infix fun <reified E> Array<E>.toNotEmptyListOrElse(
    defaultValue: (Array<E>) -> NotEmptyList<E>
): NotEmptyList<E> = toNotEmptyListOrNull() ?: defaultValue(this)

/**
 * Returns a not empty list containing all the elements of this collection, or
 * returns the result of calling the [defaultValue] function if this collection
 * is empty.
 */
@SinceKotoolsTypes("1.3")
public inline infix fun <reified E> Collection<E>.toNotEmptyListOrElse(
    defaultValue: (Collection<E>) -> NotEmptyList<E>
): NotEmptyList<E> = toNotEmptyListOrNull() ?: defaultValue(this)

/**
 * Represents lists that can't be empty.
 *
 * @param E The type of elements contained in this list.
 *
 * @constructor Creates a not empty list starting with a [head] and containing
 * all the elements of the optional [tail].
 */
@SinceKotoolsTypes("1.3")
public class NotEmptyList<out E>(override val head: E, vararg tail: E) :
    AbstractList<E>(),
    NotEmptyCollection<E> {
    private val tail: List<E>

    init {
        this.tail = tail.toList()
    }

    // ---------- Query operations ----------

    override val size: Int get() = tail.size + super.size

    override fun isEmpty(): Boolean = super<NotEmptyCollection>.isEmpty()

    // ---------- Positional Access Operations ----------

    override fun get(index: Int): E = if (index == 0) head else tail[index - 1]

    /**
     * Returns the element at the specified [index] in the list, or throws an
     * [IndexOutOfBoundsException] if the [index] is out of bounds.
     */
    @Throws(IndexOutOfBoundsException::class)
    public infix operator fun get(index: PositiveInt): E = get(index.value)

    /**
     * Returns the element at the specified [index] in the list, or throws an
     * [IndexOutOfBoundsException] if the [index] is out of bounds.
     */
    @Throws(IndexOutOfBoundsException::class)
    public infix operator fun get(index: StrictlyPositiveInt): E =
        get(index.value)

    /**
     * Returns the element at the specified [index] in the list, or returns
     * `null` if the [index] is out of bounds.
     */
    public infix fun getOrNull(index: PositiveInt): E? = getOrNull(index.value)

    /**
     * Returns the element at the specified [index] in the list, or returns
     * `null` if the [index] is out of bounds.
     */
    public infix fun getOrNull(index: StrictlyPositiveInt): E? =
        getOrNull(index.value)

    /**
     * Returns the element at the specified [index] in the list, or returns the
     * result of calling the [defaultValue] function if the [index] is out of
     * bounds.
     */
    public inline fun getOrElse(
        index: PositiveInt,
        defaultValue: (PositiveInt) -> @UnsafeVariance E
    ): E = getOrNull(index) ?: defaultValue(index)

    /**
     * Returns the element at the specified [index] in the list, or returns the
     * result of calling the [defaultValue] function if the [index] is out of
     * bounds.
     */
    public inline fun getOrElse(
        index: StrictlyPositiveInt,
        defaultValue: (StrictlyPositiveInt) -> @UnsafeVariance E
    ): E = getOrNull(index) ?: defaultValue(index)

    // ---------- Conversions ----------

    /** Returns the string representation of this list as a not blank string. */
    public fun toNotBlankString(): NotBlankString = NotBlankString(toString())
}
