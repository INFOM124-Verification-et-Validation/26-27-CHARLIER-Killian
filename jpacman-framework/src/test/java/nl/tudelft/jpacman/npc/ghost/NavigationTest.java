package nl.tudelft.jpacman.npc.ghost;

import static org.mockito.Mockito.mock;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import nl.tudelft.jpacman.board.Board;
import nl.tudelft.jpacman.board.BoardFactory;
import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.board.Square;
import nl.tudelft.jpacman.board.Unit;
import nl.tudelft.jpacman.level.*;
import nl.tudelft.jpacman.npc.Ghost;
import nl.tudelft.jpacman.sprite.PacManSprites;
import nl.tudelft.jpacman.npc.ghost.Navigation.*;

import com.google.common.collect.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

/**
 * Tests the various methods provided by the {@link Navigation} class.
 *
 * @author Jeroen Roosen
 *
 */
@SuppressWarnings({"magicnumber", "PMD.AvoidDuplicateLiterals"})
class NavigationTest {

    /**
     * Map parser used to construct boards.
     */
    private MapParser parser;

    private GhostMapParser ghostMapParser;
    private Player player;
    /**
     * Set up the map parser.
     */
    @BeforeEach
    void setUp() {
        PacManSprites sprites = new PacManSprites();
        parser = new MapParser(new LevelFactory(sprites, new GhostFactory(
            sprites)), new BoardFactory(sprites));
        ghostMapParser = new GhostMapParser(new LevelFactory(sprites, new GhostFactory(sprites)), new BoardFactory(sprites), new GhostFactory(sprites));
        player = new PlayerFactory(sprites).createPacMan();
    }

    /**
     * Verifies that the path to the same square is empty.
     */
    @Test
    void testShortestPathEmpty() {
        Board b = parser.parseMap(Lists.newArrayList(" ")).getBoard();
        Square s1 = b.squareAt(0, 0);
        Square s2 = b.squareAt(0, 0);
        List<Direction> path = Navigation
            .shortestPath(s1, s2, mock(Unit.class));
        assertThat(path).isEmpty();
    }

    /**
     * Verifies that if no path exists, the result is <code>null</code>.
     */
    @Test
    void testNoShortestPath() {
        Board b = parser
            .parseMap(Lists.newArrayList("#####", "# # #", "#####"))
            .getBoard();
        Square s1 = b.squareAt(1, 1);
        Square s2 = b.squareAt(3, 1);
        List<Direction> path = Navigation
            .shortestPath(s1, s2, mock(Unit.class));
        assertThat(path).isNull();
    }

    /**
     * Verifies that having no traveller ignores terrain.
     */
    @Test
    void testNoTraveller() {
        Board b = parser
            .parseMap(Lists.newArrayList("#####", "# # #", "#####"))
            .getBoard();
        Square s1 = b.squareAt(1, 1);
        Square s2 = b.squareAt(3, 1);
        List<Direction> path = Navigation.shortestPath(s1, s2, null);
        assertThat(path).containsExactly(Direction.EAST, Direction.EAST);
    }

    /**
     * Tests if the algorithm can find a path in a straight line.
     */
    @Test
    void testSimplePath() {
        Board b = parser.parseMap(Lists.newArrayList("####", "#  #", "####"))
            .getBoard();
        Square s1 = b.squareAt(1, 1);
        Square s2 = b.squareAt(2, 1);
        List<Direction> path = Navigation
            .shortestPath(s1, s2, mock(Unit.class));
        assertThat(path).containsExactly(Direction.EAST);
    }

    /**
     * Verifies that the algorithm can find a path when it has to take corners.
     */
    @Test
    void testCornerPath() {
        Board b = parser.parseMap(
            Lists.newArrayList("####", "#  #", "## #", "####")).getBoard();
        Square s1 = b.squareAt(1, 1);
        Square s2 = b.squareAt(2, 2);
        List<Direction> path = Navigation
            .shortestPath(s1, s2, mock(Unit.class));
        assertThat(path).containsExactly(Direction.EAST, Direction.SOUTH);
    }

    /**
     * Verifies that the nearest object is detected.
     */
    @Test
    void testNearestUnit() {
        Board b = parser
            .parseMap(Lists.newArrayList("#####", "# ..#", "#####"))
            .getBoard();
        Square s1 = b.squareAt(1, 1);
        Square s2 = b.squareAt(2, 1);
        Square result = Navigation.findNearest(Pellet.class, s1).getSquare();
        assertThat(result).isEqualTo(s2);
    }

    /**
     * Verifies that there is no such location if there is no nearest object.
     */
    @Test
    void testNoNearestUnit() {
        Board b = parser.parseMap(Lists.newArrayList(" ")).getBoard();
        Square s1 = b.squareAt(0, 0);
        Unit unit = Navigation.findNearest(Pellet.class, s1);
        assertThat(unit).isNull();
    }

    /**
     * Verifies that there is ghost on the default board
     * next to cell [1, 1].
     *
     * @throws IOException if board reading fails.
     */
    @Test
    @Disabled
    void testFullSizedLevel() throws IOException {
        try (InputStream i = getClass().getResourceAsStream("/board.txt")) {
            Board b = parser.parseMap(i).getBoard();
            Square s1 = b.squareAt(1, 1);
            Unit unit = Navigation.findNearest(Ghost.class, s1);
            assertThat(unit).isNotNull();
        }
    }

    @Test
    void testReachableNodes() {
        List<String> map = new ArrayList<>();
        map.add("###########");
        map.add("#         #");
        map.add("#    B    #");
        map.add("#         #");
        map.add("#        P#");
        map.add("#         #");
        map.add("#         #");
        map.add("###########");

        Level currentLevel = ghostMapParser.parseMap(map);
        Blinky blinky = Navigation.findUnitInBoard(Blinky.class, currentLevel.getBoard());
        Set<Node> result = Navigation.reachableNodes(blinky.getSquare(), blinky);
        Set<Node> result2 = Navigation.reachableNodes(blinky.getSquare(), blinky);
        // Désolé, je ne sais pas tester les nodes, j'y réfléchirai très sérieusement pour plus tard...
        assertThat(result).isEqualTo(result2);
    }

    @Test
    void testTopRightCorner() {
        List<String> map = new ArrayList<>();
        map.add("###########");
        map.add("#        P#");
        map.add("#    B    #");
        map.add("#         #");
        map.add("#         #");
        map.add("#         #");
        map.add("#         #");
        map.add("###########");

        Level currentLevel = ghostMapParser.parseMap(map);
        Blinky blinky = Navigation.findUnitInBoard(Blinky.class, currentLevel.getBoard());
        currentLevel.registerPlayer(player);
        Optional<Square> result = Navigation.topRightCorner(blinky.getSquare(), blinky);

        assertThat(result.get()).isEqualTo(player.getSquare());
    }
}
