
package com.github.xwanlion.lifeauctioneer;

import com.github.xwanlion.lifeauctioneer.repository.AuctioneerRepository;
import com.github.xwanlion.lifeauctioneer.repository.AuctionsRepository;
import com.github.xwanlion.lifeauctioneer.repository.BidLogRepository;
import com.github.xwanlion.lifeauctioneer.repository.BidderMoneyRepository;
import com.github.xwanlion.lifeauctioneer.repository.BidderRepository;
import com.github.xwanlion.lifeauctioneer.repository.LotRepository;
import com.github.xwanlion.lifeauctioneer.service.AuctionsServiceImpl;
import com.github.xwanlion.lifeauctioneer.service.AuctioneerServiceImpl;
import com.github.xwanlion.lifeauctioneer.service.BidLogServiceImpl;
import com.github.xwanlion.lifeauctioneer.service.BidderMoneyServiceImpl;
import com.github.xwanlion.lifeauctioneer.service.BidderServiceImpl;
import com.github.xwanlion.lifeauctioneer.service.IAuctionsService;
import com.github.xwanlion.lifeauctioneer.service.IAuctioneerService;
import com.github.xwanlion.lifeauctioneer.service.IBidLogService;
import com.github.xwanlion.lifeauctioneer.service.IBidderMoneyService;
import com.github.xwanlion.lifeauctioneer.service.IBidderService;
import com.github.xwanlion.lifeauctioneer.service.ILotService;
import com.github.xwanlion.lifeauctioneer.service.ITestimonialsService;
import com.github.xwanlion.lifeauctioneer.service.LotServiceImpl;
import com.github.xwanlion.lifeauctioneer.service.TestimonialsServiceImpl;
import com.github.xwanlion.lifeauctioneer.socket.SocketServer;

/**
 * Bean manager for creating service class in one place.
 */
public class BeanManager {
    public static ILotService lotService = null;
    public static IBidLogService bidLogService = null;
    public static IBidderService bidderService = null;
    public static IAuctionsService auctionsService = null;
    public static IAuctioneerService auctioneerService = null;
    public static IBidderMoneyService bidderMoneyService = null;
    public static ITestimonialsService testimonialsService = null;

    public static AuctionsRepository auctionsRepository = null;
    public static LotRepository lotRepository = null;
    public static BidderRepository bidderRepository = null;
    public static AuctioneerRepository auctioneerRepository = null;
    public static BidderMoneyRepository bidderMoneyRepository = null;
    public static BidLogRepository bidLogRepository = null;

//    public static SocketServer webSocketServer = null;

    static {
        lotService = new LotServiceImpl();
        bidLogService = new BidLogServiceImpl();
        bidderService = new BidderServiceImpl();
        auctionsService = new AuctionsServiceImpl();
        auctioneerService = new AuctioneerServiceImpl();
        bidderMoneyService = new BidderMoneyServiceImpl();
        testimonialsService = new TestimonialsServiceImpl();

        auctionsRepository = new AuctionsRepository();
        lotRepository = new LotRepository();
        bidderRepository = new BidderRepository();
        auctioneerRepository = new AuctioneerRepository();
        bidderMoneyRepository = new BidderMoneyRepository();
        bidLogRepository = new BidLogRepository();
    }

}