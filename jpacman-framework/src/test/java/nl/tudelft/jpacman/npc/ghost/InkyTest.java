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

import static org.assertj.core.api.Assertions.assertThat;

public class InkyTest {
    PacManSprites sprites;
    GhostFactory ghostFactory;
    LevelFactory levelFactory;
    BoardFactory boardFactory;
    GhostMapParser ghostMapParser;
    Player player;

    @Before
    public void setUp() {
        this.sprites = new PacManSprites();
        this.ghostFactory = new GhostFactory(sprites);
        this.levelFactory = new LevelFactory(sprites, ghostFactory);
        this.boardFactory = new BoardFactory(sprites);
        this.ghostMapParser = new GhostMapParser(levelFactory, boardFactory, ghostFactory);
        player = new PlayerFactory(sprites).createPacMan();
    }

    @Test
    public void testInkyInFrontOfPlayerAndBlinkyFarAway() {
        List<String> map = new ArrayList<>();
        map.add("###########");
        map.add("#P I  #   #");
        map.add("#         #");
        map.add("#         #");
        map.add("#         #");
        map.add("#        B#");
        map.add("###########");

        Level currentLevel = ghostMapParser.parseMap(map);
        currentLevel.registerPlayer(player);
        Inky inky = Navigation.findUnitInBoard(Inky.class, currentLevel.getBoard());

        Optional<Direction> aiDirection = inky.nextAiMove();

        assertThat(aiDirection.get()).isIn(new Direction[]{Direction.SOUTH, Direction.EAST});
    }

    @Test
    public void testInkyInFrontOfPlayerAndBlinkyFarAwayWithWalls() {
        List<String> map = new ArrayList<>();
        map.add("###########");
        map.add("#P I  #   #");
        map.add("#     #   #");
        map.add("#     #   #");
        map.add("##### #   #");
        map.add("#        B#");
        map.add("###########");

        Level currentLevel = ghostMapParser.parseMap(map);
        currentLevel.registerPlayer(player);
        Inky inky = Navigation.findUnitInBoard(Inky.class, currentLevel.getBoard());

        Optional<Direction> aiDirection = inky.nextAiMove();

        assertThat(aiDirection.get()).isIn(new Direction[]{Direction.SOUTH, Direction.EAST});
    }

    @Test
    public void testInkyInFrontOfPlayerAndBlinkyFarAwayBlocked() {
        List<String> map = new ArrayList<>();
        map.add("###########");
        map.add("#P I  #   #");
        map.add("#     #   #");
        map.add("#     #   #");
        map.add("#     #   #");
        map.add("#     #  B#");
        map.add("###########");

        Level currentLevel = ghostMapParser.parseMap(map);
        currentLevel.registerPlayer(player);
        Inky inky = Navigation.findUnitInBoard(Inky.class, currentLevel.getBoard());

        Optional<Direction> aiDirection = inky.nextAiMove();

        // assertThat(aiDirection.get()).isIn(new Direction[]{Direction.SOUTH, Direction.EAST});
    }

    @Test
    public void testInkyBlocked() {
        List<String> map = new ArrayList<>();
        map.add("###########");
        map.add("#P#I# #   #");
        map.add("#  #  #   #");
        map.add("#     #   #");
        map.add("#     #   #");
        map.add("#     #  B#");
        map.add("###########");

        Level currentLevel = ghostMapParser.parseMap(map);
        currentLevel.registerPlayer(player);
        Inky inky = Navigation.findUnitInBoard(Inky.class, currentLevel.getBoard());

        Optional<Direction> aiDirection = inky.nextAiMove();

        assertThat(aiDirection.isEmpty()).isTrue();
    }

    @Test
    public void testInkyInFrontOfPlayerAndBlinkyFarAwayAndBlocked() {
        List<String> map = new ArrayList<>();
        map.add("###########");
        map.add("#P I  #   #");
        map.add("#     #   #");
        map.add("#     #   #");
        map.add("#        ##");
        map.add("#       #B#");
        map.add("###########");

        Level currentLevel = ghostMapParser.parseMap(map);
        currentLevel.registerPlayer(player);
        Inky inky = Navigation.findUnitInBoard(Inky.class, currentLevel.getBoard());

        Optional<Direction> aiDirection = inky.nextAiMove();

        assertThat(aiDirection.get()).isIn(new Direction[]{Direction.SOUTH, Direction.EAST});
    }

    @Test
    public void testInkyInFrontOfPlayerAndBlinkyFarAwayAndBlockedWithWalls() {
        List<String> map = new ArrayList<>();
        map.add("###########");
        map.add("#P I  #   #");
        map.add("#     #   #");
        map.add("#     #   #");
        map.add("#     #  ##");
        map.add("#     # #B#");
        map.add("###########");

        Level currentLevel = ghostMapParser.parseMap(map);
        currentLevel.registerPlayer(player);
        Inky inky = Navigation.findUnitInBoard(Inky.class, currentLevel.getBoard());

        Optional<Direction> aiDirection = inky.nextAiMove();

        // assertThat(aiDirection.get()).isIn(new Direction[]{Direction.SOUTH, Direction.EAST});
    }

    @Test
    public void testInkyBlockedAndBlinkyBlocked() {
        List<String> map = new ArrayList<>();
        map.add("###########");
        map.add("#P#I# #   #");
        map.add("#  #  #   #");
        map.add("#     #   #");
        map.add("#     #  ##");
        map.add("#       #B#");
        map.add("###########");

        Level currentLevel = ghostMapParser.parseMap(map);
        currentLevel.registerPlayer(player);
        Inky inky = Navigation.findUnitInBoard(Inky.class, currentLevel.getBoard());

        Optional<Direction> aiDirection = inky.nextAiMove();

        assertThat(aiDirection.isEmpty()).isTrue();
    }

    @Test
    public void testInkyBlockedAndBlinkyBlockedWithWalls() {
        List<String> map = new ArrayList<>();
        map.add("###########");
        map.add("#P#I# #   #");
        map.add("#  #  #   #");
        map.add("#     #   #");
        map.add("#     #  ##");
        map.add("#     # #B#");
        map.add("###########");

        Level currentLevel = ghostMapParser.parseMap(map);
        currentLevel.registerPlayer(player);
        Inky inky = Navigation.findUnitInBoard(Inky.class, currentLevel.getBoard());

        Optional<Direction> aiDirection = inky.nextAiMove();

        assertThat(aiDirection.isEmpty()).isTrue();
    }

    // Peut-être gérer les directions dans lesquelles Blinky et Joueur regarde
    @Test
    public void testInkyBehindBlinkyNotThatFarFromPlayer() {
        List<String> map = new ArrayList<>();
        map.add("###########");
        map.add("#P    #   #");
        map.add("#     #   #");
        map.add("# B   #   #");
        map.add("#        ##");
        map.add("# I       #");
        map.add("###########");

        Level currentLevel = ghostMapParser.parseMap(map);
        currentLevel.registerPlayer(player);
        Inky inky = Navigation.findUnitInBoard(Inky.class, currentLevel.getBoard());

        Optional<Direction> aiDirection = inky.nextAiMove();

        // assertThat(aiDirection.get()).isEqualTo(Direction.NORTH);
    }

    @Test
    public void testInkyBehindBlinkyFarFromPlayer() {
        List<String> map = new ArrayList<>();
        map.add("###########");
        map.add("#     #   #");
        map.add("#  P  #   #");
        map.add("#     #   #");
        map.add("#        ##");
        map.add("#     B  I#");
        map.add("###########");

        Level currentLevel = ghostMapParser.parseMap(map);
        currentLevel.registerPlayer(player);
        Inky inky = Navigation.findUnitInBoard(Inky.class, currentLevel.getBoard());

        Optional<Direction> aiDirection = inky.nextAiMove();

        // assertThat(aiDirection.get()).isEqualTo(Direction.WEST);
    }

    @Test
    public void testInkywithLargerMap() {
        List<String> map = new ArrayList<>();
        map.add("################");
        map.add("#  #           #");
        map.add("#  P  ### ### ##");
        map.add("#  #  ##   #   #");
        map.add("#        # ##  #");
        map.add("#  #    ##     #");
        map.add("# ##   #####  ##");
        map.add("# ##     ###  ##");
        map.add("#     B       I#");
        map.add("################");

        Level currentLevel = ghostMapParser.parseMap(map);
        currentLevel.registerPlayer(player);
        Inky inky = Navigation.findUnitInBoard(Inky.class, currentLevel.getBoard());

        Optional<Direction> aiDirection = inky.nextAiMove();

        assertThat(aiDirection.get()).isEqualTo(Direction.WEST);
    }
}
