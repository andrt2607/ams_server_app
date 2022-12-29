package mii.mcc72.ams_server_app.repos;

import mii.mcc72.ams_server_app.models.Asset;
import mii.mcc72.ams_server_app.utils.AssetStatus;
import mii.mcc72.ams_server_app.utils.RentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface AssetRepo extends JpaRepository<Asset, Integer> {

    boolean existsAssetByName(String name);
    Optional<Asset> findByName(String name);

    @Modifying
    @Transactional
    @Query("UPDATE Asset a " + "SET a.qty = qty - 1" + "WHERE a.id = ?1")
    void decreaseQtyAfterBroken(int id);

    @Transactional
    @Modifying
    @Query("UPDATE Asset a " + "SET a.approvedStatus = ?2 " + "WHERE a.id = ?1")
    void reviewSubmissionRequest(int id, AssetStatus assetStatus);

    List<Asset> findAllByApprovedStatusEquals(AssetStatus assetStatus);
}
