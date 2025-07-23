package ir.welunch.app.mapper;

import ir.welunch.app.entity.Transaction;
import ir.welunch.core.model.TransactionModel;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(config = GenericMapper.class, builder = @Builder(disableBuilder = true))
public interface TransactionMapper {
    TransactionModel toModel(Transaction entity);

    Transaction toEntity(TransactionModel model);

    List<TransactionModel> toModelList(List<Transaction> entityList);

    List<Transaction> toEntityList(List<TransactionModel> modelList);
}
