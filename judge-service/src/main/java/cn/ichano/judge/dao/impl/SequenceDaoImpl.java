package cn.ichano.judge.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.ichano.common.config.Env;
import cn.ichano.common.db.dao.IBaseDao;
import cn.ichano.judge.dao.SequenceDao;
import cn.ichano.judge.entity.Sequence;


@Service
public class SequenceDaoImpl implements SequenceDao {

	@Autowired
	private IBaseDao baseDao;
	
	@Override
	public List<Sequence> querySequenceList(int serverId) {
		String sql = Env.getSqlResource().getValue("seq.query_by_serverid");

		Sequence sequence = new Sequence();
		sequence.setServerId(serverId);
		return baseDao.queryList(sql, sequence);
	}

	@Override
	public Long getNext(int serverId, String entity, long max) {
		String sql = Env.getSqlResource().getValue("seq.update_seq");

		Sequence sequence = new Sequence();
		sequence.setServerId(serverId);
		sequence.setEntity(entity);
		sequence.setCurrent(max);
		baseDao.update(sql, null, sequence);
		return max;
	}

	@Override
	public Sequence querySingleSequence(int serverId, String entity) {
		String sql = Env.getSqlResource().getValue("seq.query_by_entity");

		Sequence sequence = new Sequence();
		sequence.setServerId(serverId);
		sequence.setEntity(entity);

		return baseDao.querySingle(sql, Sequence.class, sequence);
	}
}
