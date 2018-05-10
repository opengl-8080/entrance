package entrance.view.javafx.component

import entrance.domain.Rank
import entrance.domain.RankCondition
import entrance.view.javafx.util.FXPrototypeController
import javafx.beans.property.ReadOnlyObjectWrapper
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.ChoiceBox
import javafx.util.StringConverter
import java.net.URL
import java.util.*

@FXPrototypeController
class RankSelectController: Initializable {
    @FXML
    lateinit var minRankChoiceBox: ChoiceBox<Rank>
    @FXML
    lateinit var maxRankChoiceBox: ChoiceBox<Rank>
    
    private val _minRank = ReadOnlyObjectWrapper<Rank>(Rank.THREE)
    var minRank: Rank
        get() = _minRank.value
        set(value) = minRankChoiceBox.selectionModel.select(value)
    
    private val _maxRank = ReadOnlyObjectWrapper<Rank>(Rank.FIVE)
    
    val condition: RankCondition
        get() = RankCondition(max= _maxRank.value, min= _minRank.value)

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        val ranks = Rank.values().sortedBy { it.value }
        minRankChoiceBox.items.addAll(ranks)
        maxRankChoiceBox.items.addAll(ranks)
        
        minRankChoiceBox.converter = RankStringConverter
        maxRankChoiceBox.converter = RankStringConverter
        
        minRankChoiceBox.selectionModel.select(_minRank.value)
        maxRankChoiceBox.selectionModel.select(_maxRank.value)
        
        _minRank.bind(minRankChoiceBox.selectionModel.selectedItemProperty())
        _maxRank.bind(maxRankChoiceBox.selectionModel.selectedItemProperty())
    }
}

private object RankStringConverter: StringConverter<Rank>() {
    override fun toString(rank: Rank): String = rank.value.toString()
    override fun fromString(string: String): Rank = Rank.of(string.toInt())
}
