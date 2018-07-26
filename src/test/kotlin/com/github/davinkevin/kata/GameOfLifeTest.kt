package com.github.davinkevin.kata

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class GameOfLifeTest {

    @Nested
    inner class Initialisation {

        @Test
        fun `should have no living cells at beginning`() {
            /* Given */
            /* When */
            /* Then */
            assertThat(Grid(5, 5).cells).isEmpty()
        }

        @Test
        fun `should not allow add of cells out of the scope`() {
            /* Given */
            val grid = Grid(5, 5)
            /* When */
            assertThatThrownBy { grid.add(Cell(10, 10)) }
            /* Then */
                    .isInstanceOf(RuntimeException::class.java)
        }
    }

    @Nested
    inner class WithOneCell {

        lateinit var grid: Grid

        @BeforeEach fun beforeEach() {
            grid = Grid(5, 5)
                    .add(Cell(1, 3))
        }

        @Test
        fun `second generation should lead to no cells at all`() {
            /* Given */
            /* When */
            val secondGeneration = grid.computeNextGeneration()
            /* Then */
            assertThat(secondGeneration.cells).isEmpty()
        }
    }

    @Nested
    inner class WithTwoCells {
        lateinit var grid: Grid

        @BeforeEach fun beforeEach() {
            grid = Grid(5, 5)
                    .add(Cell(1, 3))
                    .add(Cell(1, 4))
        }

        @Test
        fun `second generation should lead to no cells at all`() {
            /* Given */
            /* When */
            val secondGeneration = grid.computeNextGeneration()
            /* Then */
            assertThat(secondGeneration.cells).isEmpty()
        }
    }

    @Nested
    inner class WithThreeCells {
        lateinit var grid: Grid

        @BeforeEach fun beforeEach() {
            grid = Grid(5, 5)
                    .add(Cell(1, 3))
                    .add(Cell(1, 4))
                    .add(Cell(2, 4))
        }

        @Test
        fun `second generation should lead to new cells`() {
            /* Given */
            /* When */
            val secondGeneration = grid.computeNextGeneration()
            /* Then */
            assertThat(secondGeneration.cells).containsAll(grid.cells)
        }
    }

    @Nested
    inner class WithResurection {
        lateinit var grid: Grid

        @BeforeEach fun beforeEach() {
            grid = Grid(5, 5)
                    .add(Cell(1, 2))
                    .add(Cell(1, 3))
                    .add(Cell(2, 3))
        }

        @Test
        fun `second generation should lead to new cells`() {
            /* Given */
            /* When */
            val secondGeneration = grid.computeNextGeneration()
            /* Then */
            assertThat(secondGeneration.cells)
                    .containsAll(grid.cells)
                    .contains(Cell(2, 3))
        }
    }


}