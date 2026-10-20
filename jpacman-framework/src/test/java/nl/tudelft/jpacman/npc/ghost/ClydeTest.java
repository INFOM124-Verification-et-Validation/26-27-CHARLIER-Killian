package nl.tudelft.jpacman.npc.ghost;

import nl.tudelft.jpacman.board.BoardFactory;
import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.level.Level;
import nl.tudelft.jpacman.level.LevelFactory;
import nl.tudelft.jpacman.level.Player;
import nl.tudelft.jpacman.level.PlayerFactory;
import nl.tudelft.jpacman.sprite.PacManSprites;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

public class ClydeTest {
    /**
     *
     */
    @Test
    public void testNextAiMove() {
        List<String> map = new ArrayList<>();
        map.add("###########");
        map.add("#PC#      #");
        map.add("# ###     #");
        map.add("#    #    #");
        map.add("#   # #   #");
        map.add("#         #");
        map.add("###########");

        //  "###########" +
        //  "#         #" +
        //  "#  #C#    #" +
        //  "# ###     #" +
        //  "#     ### #" +
        //  "#        P#" +
        //  "###########"

        PacManSprites pacManSprites = new PacManSprites();
        GhostFactory ghostFactory = new GhostFactory(pacManSprites);
        LevelFactory levelFactory = new LevelFactory(pacManSprites, ghostFactory);
        BoardFactory boardFactory = new BoardFactory(pacManSprites);
        GhostMapParser ghostMapParser = new GhostMapParser(levelFactory, boardFactory, ghostFactory);
        Player player = new PlayerFactory(pacManSprites).createPacMan();

        Level currentLevel = ghostMapParser.parseMap(map);
        currentLevel.registerPlayer(player);
        Clyde clyde = Navigation.findUnitInBoard(Clyde.class, currentLevel.getBoard());

        Optional<Direction> aiDirection = clyde.nextAiMove();

        assertThat(aiDirection).isIn(Direction.SOUTH, Direction.EAST);
    }

    // Une partition détermine les comportements similaires
}
