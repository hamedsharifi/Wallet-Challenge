package ir.welunch.app.mapper;


import ir.welunch.app.entity.Wallet;
import model.WalletModel;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(config = GenericMapper.class, builder = @Builder(disableBuilder = true))
public interface WalletMapper {
    WalletModel toModel(Wallet entity);

    Wallet toEntity(WalletModel model);

    List<WalletModel> toModelList(List<Wallet> entityList);

    List<Wallet> toEntityList(List<WalletModel> modelList);
}
