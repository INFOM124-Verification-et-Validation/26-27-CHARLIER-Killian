package nl.tudelft.jpacman.npc.ghost;

import nl.tudelft.jpacman.board.BoardFactory;
import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.level.Level;
import nl.tudelft.jpacman.level.LevelFactory;
import nl.tudelft.jpacman.level.Player;
import nl.tudelft.jpacman.level.PlayerFactory;
import nl.tudelft.jpacman.sprite.PacManSprites;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

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

        assertThat(aiDirection.get()).isEqualTo(Direction.EAST);
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

        assertThat(aiDirection.get()).isEqualTo(Direction.SOUTH);
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

        assertThat(aiDirection.get()).isIn(new Direction[]{Direction.NORTH, Direction.WEST});
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

        assertThat(aiDirection.get()).isIn(new Direction[]{Direction.SOUTH, Direction.EAST});
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

class ClydeTestBis {
    PacManSprites sprites = new PacManSprites();
    GhostFactory ghostFactory = new GhostFactory(sprites);
    LevelFactory levelFactory = new LevelFactory(sprites, ghostFactory);
    BoardFactory boardFactory = new BoardFactory(sprites);
    public MapParser mapParser = new GhostMapParser(levelFactory, boardFactory, ghostFactory);
    public PlayerFactory playerFactory = new PlayerFactory(sprites);


    // T1
    @Test
    void clydeShouldGoAwayFromPacmanWhenCloseTest() {
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
    void clydeShouldNotMoveTest() {
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
    void clydeShouldGetCloserToPacManWhenFarTest() {
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
