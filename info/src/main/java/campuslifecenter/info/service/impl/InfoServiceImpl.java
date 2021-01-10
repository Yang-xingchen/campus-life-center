package campuslifecenter.info.service.impl;

import campuslifecenter.info.entry.*;
import campuslifecenter.info.mapper.*;
import campuslifecenter.info.model.AddInfoRequest;
import campuslifecenter.info.service.InfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class InfoServiceImpl implements InfoService {

    @Autowired
    private InfoMapper infoMapper;
    @Autowired
    private InfoTextMapper textMapper;
    @Autowired
    private InfoArrayMapper arrayMapper;
    @Autowired
    private InfoRadioMapper radioMapper;

    @Autowired
    private InfoListMapper infoListMapper;
    @Autowired
    private AccountInfoMapper accountInfoMapper;

    @Override
    public String addInfoCollect(AddInfoRequest addInfoRequest) {
        String uuid = UUID.randomUUID().toString();
        addInfoRequest.getInfos()
                .forEach(infoCollect -> insertCollect(infoCollect, addInfoRequest.getAids(), uuid));
        return uuid;
    }

    @SuppressWarnings("AlibabaSwitchStatement")
    private long insertCollect(AddInfoRequest.InfoCollect infoCollect, List<String> aids, String ref) {
        if (!infoCollect.isExist()) {
            Info info = infoCollect.toInfo();
            infoMapper.insert(info);
            infoCollect.setId(info.getId());
            switch (infoCollect.getType()) {
                case 0 -> textMapper.insert(infoCollect.toText());
                case 1 -> infoCollect
                        .getArrayInfo()
                        .stream()
                        .map(item -> {
                            long id = insertCollect(item, aids, ref);
                            return new InfoArray().withId(id).withPid(infoCollect.getId());
                        })
                        .forEach(arrayMapper::insert);
                case 2 -> infoCollect.getRadioInfo().forEach(s -> radioMapper.insert(
                        new InfoRadioKey().withId(infoCollect.getId()).withText(s)
                ));
                default -> throw new IllegalArgumentException("illegal info type.");
            }
        } else {
            Info info = infoMapper.selectByPrimaryKey(infoCollect.getId());
            infoCollect.setDefaultVisibility(info.getDefaultVisibility());
        }
        InfoList infoList = new InfoList().withListOrder(infoCollect.getOrder());
        infoList.withId(infoCollect.getId()).withSource(ref);
        infoListMapper.insert(infoList);
        aids.forEach(s -> {
            AccountInfo accountInfo = new AccountInfo()
                    .withVisibility(infoCollect.getDefaultVisibility());
            accountInfo.withAid(s).withId(infoList.getId());
            accountInfoMapper.insert(accountInfo);
        });
        return infoCollect.getId();
    }

    @Override
    public List<String> select(long id, String text) {
        AccountInfoExample example = new AccountInfoExample();
        example.createCriteria().andIdEqualTo(id).andTextLike(text);
        return accountInfoMapper
                .selectByExample(example)
                .stream()
                .map(AccountInfoKey::getAid)
                .collect(Collectors.toList());
    }
}
