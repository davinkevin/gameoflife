package com.github.davinkevin.kata

class Grid(private val width: Int, private val height: Int, val cells: Set<Cell> = setOf()) {

    fun add(c: Cell): Grid {
        if(!isInGrid(c)) {
            throw RuntimeException("Not valid in this grid")
        }
        return Grid(width, height, cells + c)
    }

    fun computeNextGeneration(): Grid {
        if(cells.isEmpty()) {
            return this
        }

        val currentCells = cells
                .map { it to it.livingNeighbours(this) }
                .flatMap { when {
                    it.second.size < 2 || it.second.size > 3 -> setOf()
                    it.second.size in 2..3 -> setOf(it.first)
                    else -> setOf()
                } }
                .toSet()

        val resurrectedCells = (0..width)
                .asSequence()
                .flatMap { x: Int ->
                    (0..height).asSequence().map { y: Int -> Cell(x, y) }
                }
                .filter { it !in cells }
                .map { it to it.neighbours(this) }
                .filter { it.second.size == 3 }
                .map { it.first }
                .toSet()


        return Grid(width, height, currentCells + resurrectedCells)
    }

    private fun isInGrid(c: Cell): Boolean {
        return c.x in 0..width && c.y in 0..height
    }

    override fun toString(): String {
        for(v in width-1 downTo 0) {
            for (w in height-1 downTo 0) {
                when {
                    Cell(v, w) in cells -> print("●")
                    else -> print(".")
                }
            }
            println("")
        }
        return super.toString()
    }
}

data class Cell(val x: Int, val y: Int) {

    fun neighbours(g: Grid): Set<Cell> {
        val neighbours = listOf(
                Cell(this.x-1, this.y+1), Cell(this.x, this.y+1), Cell(this.x+1, this.y+1),
                Cell(this.x-1, this.y), Cell(this.x+1, this.y),
                Cell(this.x-1, this.y-1), Cell(this.x, this.y-1), Cell(this.x+1, this.y-1)
        )

        return neighbours.intersect(g.cells)
    }

    fun livingNeighbours(g: Grid) = g
            .cells
            .filter { it.x in x - 1..x + 1 }
            .filter { it.y in y - 1..y + 1 }
            .filter { it != this }
            .toSet()
}

