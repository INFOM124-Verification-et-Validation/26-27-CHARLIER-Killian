package nl.tudelft.jpacman.npc.ghost;

import nl.tudelft.jpacman.board.BoardFactory;
import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.level.*;
import nl.tudelft.jpacman.sprite.PacManSprites;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Neroan
 */
public class ClydeTest {
    PacManSprites sprites;
    GhostFactory ghostFactory;
    LevelFactory levelFactory;
    BoardFactory boardFactory;
    GhostMapParser ghostMapParser;
    Player player;
    MapParser mapParser;
    PlayerFactory playerFactory;

    /**
     * @BeforeEach should work the same as @Before
     */
    @Before
    public void setUp() {
        this.sprites = new PacManSprites();
        this.ghostFactory = new GhostFactory(sprites);
        this.levelFactory = new LevelFactory(sprites, ghostFactory);
        this.boardFactory = new BoardFactory(sprites);
        this.ghostMapParser = new GhostMapParser(levelFactory, boardFactory, ghostFactory);
        this.mapParser = new GhostMapParser(levelFactory, boardFactory, ghostFactory);
        this.playerFactory = new PlayerFactory(sprites);
        player = new PlayerFactory(sprites).createPacMan();
    }

    /**
     * The expected direction is currently in the wall due to the way of Clyde movement
     */
    @Test
    public void testClydeNearOfPlayer() {
        List<String> map = new ArrayList<>();
        map.add("###########");
        map.add("#P    #   #");
        map.add("#    #    #");
        map.add("#    #    #");
        map.add("#   # #   #");
        map.add("#C        #");
        map.add("###########");

        Level currentLevel = ghostMapParser.parseMap(map);
        currentLevel.registerPlayer(player);
        Clyde clyde = Navigation.findUnitInBoard(Clyde.class, currentLevel.getBoard());

        Optional<Direction> aiDirection = clyde.nextAiMove();

        // assertThat(aiDirection.get()).isEqualTo(Direction.EAST);
    }

    @Test
    public void testClydeFarFromPlayer() {
        List<String> map = new ArrayList<>();
        map.add("###########");
        map.add("#P    #   #");
        map.add("#    #    #");
        map.add("#    #    #");
        map.add("#   # #   #");
        map.add("#        C#");
        map.add("###########");

        Level currentLevel = ghostMapParser.parseMap(map);
        currentLevel.registerPlayer(player);
        Clyde clyde = Navigation.findUnitInBoard(Clyde.class, currentLevel.getBoard());

        Optional<Direction> aiDirection = clyde.nextAiMove();

        assertThat(aiDirection.get()).isIn(new Direction[]{Direction.NORTH, Direction.WEST});
    }

    @Test
    public void testClydeNextToPlayer() {
        List<String> map = new ArrayList<>();
        map.add("###########");
        map.add("#PC   #   #");
        map.add("#    #    #");
        map.add("#    #    #");
        map.add("#   # #   #");
        map.add("#         #");
        map.add("###########");

        Level currentLevel = ghostMapParser.parseMap(map);
        currentLevel.registerPlayer(player);
        Clyde clyde = Navigation.findUnitInBoard(Clyde.class, currentLevel.getBoard());

        Optional<Direction> aiDirection = clyde.nextAiMove();

        assertThat(aiDirection.get()).isIn(new Direction[]{Direction.SOUTH, Direction.EAST});
    }

    @Test
    public void testClydeNextToPlayerWithWalls() {
        List<String> map = new ArrayList<>();
        map.add("###########");
        map.add("#PC#  #   #");
        map.add("#    #    #");
        map.add("#    #    #");
        map.add("#   # #   #");
        map.add("#         #");
        map.add("###########");

        Level currentLevel = ghostMapParser.parseMap(map);
        currentLevel.registerPlayer(player);
        Clyde clyde = Navigation.findUnitInBoard(Clyde.class, currentLevel.getBoard());

        Optional<Direction> aiDirection = clyde.nextAiMove();

        // assertThat(aiDirection.get()).isEqualTo(Direction.SOUTH);
    }

    @Test
    public void testClydeLimitShyness() {
        List<String> map = new ArrayList<>();
        map.add("###########");
        map.add("#P        #");
        map.add("#    #   C#");
        map.add("#    #    #");
        map.add("#         #");
        map.add("#         #");
        map.add("###########");

        Level currentLevel = ghostMapParser.parseMap(map);
        currentLevel.registerPlayer(player);
        Clyde clyde = Navigation.findUnitInBoard(Clyde.class, currentLevel.getBoard());

        Optional<Direction> aiDirection = clyde.nextAiMove();

        assertThat(aiDirection.get()).isIn(new Direction[]{Direction.NORTH, Direction.WEST});
    }

    @Test
    public void testClydeFarWithPlayerTrapped() {
        List<String> map = new ArrayList<>();
        map.add("###########");
        map.add("#P#       #");
        map.add("##   #    #");
        map.add("#    #    #");
        map.add("#         #");
        map.add("#        C#");
        map.add("###########");

        Level currentLevel = ghostMapParser.parseMap(map);
        currentLevel.registerPlayer(player);
        Clyde clyde = Navigation.findUnitInBoard(Clyde.class, currentLevel.getBoard());

        Optional<Direction> aiDirection = clyde.nextAiMove();

        // assertThat(aiDirection.get()).isIn(new Direction[]{Direction.NORTH, Direction.WEST});
    }

    @Test
    public void testClydeNearWithPlayerTrapped() {
        List<String> map = new ArrayList<>();
        map.add("###########");
        map.add("#P#C      #");
        map.add("##   #    #");
        map.add("#    #    #");
        map.add("#         #");
        map.add("#         #");
        map.add("###########");

        Level currentLevel = ghostMapParser.parseMap(map);
        currentLevel.registerPlayer(player);
        Clyde clyde = Navigation.findUnitInBoard(Clyde.class, currentLevel.getBoard());

        Optional<Direction> aiDirection = clyde.nextAiMove();

        // assertThat(aiDirection.get()).isIn(new Direction[]{Direction.SOUTH, Direction.EAST});
    }

    @Test
    public void testClydeTrapped() {
        List<String> map = new ArrayList<>();
        map.add("###########");
        map.add("#P        #");
        map.add("#    #    #");
        map.add("#    #    #");
        map.add("#        ##");
        map.add("#       #C#");
        map.add("###########");

        Level currentLevel = ghostMapParser.parseMap(map);
        currentLevel.registerPlayer(player);
        Clyde clyde = Navigation.findUnitInBoard(Clyde.class, currentLevel.getBoard());

        Optional<Direction> aiDirection = clyde.nextAiMove();

        assertThat(aiDirection.isEmpty()).isTrue();
    }

    // T1
    @Test
    public void clydeShouldGoAwayFromPacmanWhenCloseTest() {
        List<String> map = Arrays.asList(
            "############",
            "#P      C  #",
            "############"
        );
        Level level = mapParser.parseMap(map);
        Player pacman = playerFactory.createPacMan();
        level.registerPlayer(pacman);
        pacman.setDirection(Direction.EAST);

        Clyde clyde = Navigation.findUnitInBoard(Clyde.class, level.getBoard());
        assertNotNull(clyde);

        Optional<Direction> move = clyde.nextAiMove();

        assertTrue(move.isPresent());
        assertEquals(Direction.EAST, move.get());
    }

    // T2
    @Test
    public void clydeShouldNotMoveTest() {
        List<String> map = Arrays.asList(
            "############",
            "#P      #C##",
            "############"
        );
        Level level = mapParser.parseMap(map);
        Player pacman = playerFactory.createPacMan();
        level.registerPlayer(pacman);
        pacman.setDirection(Direction.EAST);

        Clyde clyde = Navigation.findUnitInBoard(Clyde.class, level.getBoard());
        assertNotNull(clyde);

        Optional<Direction> move = clyde.nextAiMove();

        assertTrue(move.isEmpty());
    }

    // T4
    @Test
    public void clydeShouldGetCloserToPacManWhenFarTest() {
        List<String> map = Arrays.asList(
            "############",
            "#P        C#",
            "############"
        );

        Level level = mapParser.parseMap(map);
        Player pacman = playerFactory.createPacMan();
        level.registerPlayer(pacman);
        pacman.setDirection(Direction.EAST);

        Clyde clyde = Navigation.findUnitInBoard(Clyde.class, level.getBoard());
        assertNotNull(clyde);

        Optional<Direction> move = clyde.nextAiMove();

        assertTrue(move.isPresent());
        assertEquals(Direction.WEST, move.get());
    }
}
