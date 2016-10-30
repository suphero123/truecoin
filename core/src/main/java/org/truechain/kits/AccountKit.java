package org.truechain.kits;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.truechain.account.Account;
import org.truechain.account.Account.AccountType;
import org.truechain.account.AccountTool;
import org.truechain.account.Address;
import org.truechain.core.Coin;
import org.truechain.core.exception.MoneyNotEnoughException;
import org.truechain.crypto.ECKey;
import org.truechain.network.NetworkParameters;
import org.truechain.store.StoreProvider;
import org.truechain.store.TransactionStore;
import org.truechain.store.TransactionStoreProvider;
import org.truechain.utils.Utils;

/**
 * 账户管理
 * @author ln
 *
 */
public class AccountKit {
	
	private final static Logger log = LoggerFactory.getLogger(AccountKit.class);

	private NetworkParameters network;
	//账户文件路径
	private String accountFile;
	private List<Account> accountList = new ArrayList<Account>();
	//状态连存储服务
	private StoreProvider chainstateStoreProvider;
	//交易存储服务
	private TransactionStoreProvider transactionStoreProvider;
	
	
	public AccountKit(NetworkParameters network, String dataDir) throws IOException {
		
		this.network = Utils.checkNotNull(network);
		Utils.checkNotNull(dataDir);
		
		//帐户数据保存于数据目录下，命名为account.data
		this.accountFile = dataDir + "account.dat";
		
		//初始化交易存储服务，保存与帐户有关的所有交易，保存于数据目录下的transaction文件夹
		this.transactionStoreProvider = TransactionStoreProvider.getInstace(dataDir+File.pathSeparator+"transaction", network);
		//初始化状态链存储服务，该目录保存的所有未花费的交易，保存于数据目录下的chainstate文件夹
		this.chainstateStoreProvider = TransactionStoreProvider.getInstace(dataDir+File.pathSeparator+"chainstate", network);
				
		init();
	}
	
	/**
	 * 账户列表
	 */
	public void listAccount() {
		
	}
	
	/**
	 * 地址列表
	 */
	public void listAddress() {
		
	}
	
	/**
	 * 地址列表
	 */
	public void listAddress(String accountId) {
		
	}
	
	/**
	 * 获取余额
	 */
	public Coin getBalance() {
		return null;
	}
	
	/**
	 * 获取余额
	 */
	public Coin getBalance(String accountId) {
		return null;
	}
	
	/**
	 * 获取余额
	 */
	public Coin getBalance(Account account) {
		return null;
	}
	
	/**
	 * 获取余额
	 */
	public Coin getBalance(Address address) {
		return null;
	}
	
	/**
	 * 获取余额
	 */
	public Coin getAddressBalance(String address) {
		return null;
	}
	
	/**
	 * 获取交易列表
	 */
	public void getTransaction() {
		
	}
	
	/**
	 * 获取交易列表
	 */
	public void getTransaction(String accountId) {
		
	}
	
	/**
	 * 发送普通交易到指定地址
	 * @param to   base58的地址
	 * @param money	发送金额
	 * @param fee	手续费
	 * @return
	 * @throws MoneyNotEnoughException
	 */
	public Future sendMoney(String to, Coin money, Coin fee) throws MoneyNotEnoughException {
		//参数不能为空
		Utils.checkNotNull(to);
		Utils.checkNotNull(to);
		
		//发送的金额必须大于0
		if(money.compareTo(Coin.ZERO) <= 0) {
			throw new RuntimeException("发送的金额需大于0");
		}
		if(fee == null || fee.compareTo(Coin.ZERO) < 0) {
			fee = Coin.ZERO;
		}
		//当前余额
		Coin balance = getBalance();
		//检查余额是否充足
		if(money.add(fee).compareTo(balance) > 0) {
			throw new MoneyNotEnoughException();
		}
		
		return null;
	}
	
	/*
	 * 初始化账户信息
	 */
	private synchronized void init() throws IOException {
		//检查账户目录是否存在
		File account = new File(accountFile);
		if(!account.exists() || account.isDirectory()) {
			createNewAccount(account);
		} 
		loadAccount(account);
	}

	//创建新的钱包
	private void createNewAccount(File accountFile) throws IOException {
		//创建账户新文件
		accountFile.createNewFile();
		//生成私匙公匙对
		ECKey key = new ECKey();
		
		//默认生成一个系统帐户
		AccountType accountType = AccountType.SYSTEM;
		
		FileOutputStream fos = new FileOutputStream(accountFile);
		try {
			fos.write(accountType.value());
			fos.write(key.getPrivKeyBytes());
			fos.write(key.getPubKey());
		} finally {
			fos.close();
		}
	}

	//加载现有的钱包
	private void loadAccount(File accountFile) throws IOException {
		//读取私匙
		FileInputStream fis = new FileInputStream(accountFile);
		byte type = 0;
		byte[] privKey = new byte[32];
		try {
			byte[] typeAndPrivKey = new byte[33];
			fis.read(typeAndPrivKey);
			type = typeAndPrivKey[0];	//账户类型
			System.arraycopy(typeAndPrivKey, 1, privKey, 0, 32);
		} finally {
			fis.close();
		}
		
		ECKey key = ECKey.fromPrivate(new BigInteger(privKey));
		
		//目前先实现系统账户
		if(type == AccountType.SYSTEM.value()) {
			Address address = AccountTool.newAddress(network, AccountType.SYSTEM.value(), key);
			accountList.add(new Account().add(address));
			if(log.isDebugEnabled()) {
				log.debug("load account : {}", address.getBase58());
			}
		}
		//加载各地址的余额
		loadBalanceFromChainstateAndUnconfirmedTransaction();
	}

	/*
	 * 从状态链（未花费的地址集合）和未确认的交易加载余额
	 */
	private void loadBalanceFromChainstateAndUnconfirmedTransaction() {
		for (Account account : accountList) {
			for (Address address : account.getAddressList()) {
				byte[] hash160 = address.getHash160();
				//查询是否
				Coin[] balances = transactionStoreProvider.getBalanceAndUnconfirmedBalance(hash160);
				address.setBalance(balances[0]);
				address.setUnconfirmedBalance(balances[1]);
			}
		}
	}
}
