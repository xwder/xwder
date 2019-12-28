package com.xwder.biz.model.book;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BookChapterExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public BookChapterExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andBookNameIsNull() {
            addCriterion("book_name is null");
            return (Criteria) this;
        }

        public Criteria andBookNameIsNotNull() {
            addCriterion("book_name is not null");
            return (Criteria) this;
        }

        public Criteria andBookNameEqualTo(String value) {
            addCriterion("book_name =", value, "bookName");
            return (Criteria) this;
        }

        public Criteria andBookNameNotEqualTo(String value) {
            addCriterion("book_name <>", value, "bookName");
            return (Criteria) this;
        }

        public Criteria andBookNameGreaterThan(String value) {
            addCriterion("book_name >", value, "bookName");
            return (Criteria) this;
        }

        public Criteria andBookNameGreaterThanOrEqualTo(String value) {
            addCriterion("book_name >=", value, "bookName");
            return (Criteria) this;
        }

        public Criteria andBookNameLessThan(String value) {
            addCriterion("book_name <", value, "bookName");
            return (Criteria) this;
        }

        public Criteria andBookNameLessThanOrEqualTo(String value) {
            addCriterion("book_name <=", value, "bookName");
            return (Criteria) this;
        }

        public Criteria andBookNameLike(String value) {
            addCriterion("book_name like", value, "bookName");
            return (Criteria) this;
        }

        public Criteria andBookNameNotLike(String value) {
            addCriterion("book_name not like", value, "bookName");
            return (Criteria) this;
        }

        public Criteria andBookNameIn(List<String> values) {
            addCriterion("book_name in", values, "bookName");
            return (Criteria) this;
        }

        public Criteria andBookNameNotIn(List<String> values) {
            addCriterion("book_name not in", values, "bookName");
            return (Criteria) this;
        }

        public Criteria andBookNameBetween(String value1, String value2) {
            addCriterion("book_name between", value1, value2, "bookName");
            return (Criteria) this;
        }

        public Criteria andBookNameNotBetween(String value1, String value2) {
            addCriterion("book_name not between", value1, value2, "bookName");
            return (Criteria) this;
        }

        public Criteria andBookIdIsNull() {
            addCriterion("book_id is null");
            return (Criteria) this;
        }

        public Criteria andBookIdIsNotNull() {
            addCriterion("book_id is not null");
            return (Criteria) this;
        }

        public Criteria andBookIdEqualTo(Integer value) {
            addCriterion("book_id =", value, "bookId");
            return (Criteria) this;
        }

        public Criteria andBookIdNotEqualTo(Integer value) {
            addCriterion("book_id <>", value, "bookId");
            return (Criteria) this;
        }

        public Criteria andBookIdGreaterThan(Integer value) {
            addCriterion("book_id >", value, "bookId");
            return (Criteria) this;
        }

        public Criteria andBookIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("book_id >=", value, "bookId");
            return (Criteria) this;
        }

        public Criteria andBookIdLessThan(Integer value) {
            addCriterion("book_id <", value, "bookId");
            return (Criteria) this;
        }

        public Criteria andBookIdLessThanOrEqualTo(Integer value) {
            addCriterion("book_id <=", value, "bookId");
            return (Criteria) this;
        }

        public Criteria andBookIdIn(List<Integer> values) {
            addCriterion("book_id in", values, "bookId");
            return (Criteria) this;
        }

        public Criteria andBookIdNotIn(List<Integer> values) {
            addCriterion("book_id not in", values, "bookId");
            return (Criteria) this;
        }

        public Criteria andBookIdBetween(Integer value1, Integer value2) {
            addCriterion("book_id between", value1, value2, "bookId");
            return (Criteria) this;
        }

        public Criteria andBookIdNotBetween(Integer value1, Integer value2) {
            addCriterion("book_id not between", value1, value2, "bookId");
            return (Criteria) this;
        }

        public Criteria andAuthorIsNull() {
            addCriterion("author is null");
            return (Criteria) this;
        }

        public Criteria andAuthorIsNotNull() {
            addCriterion("author is not null");
            return (Criteria) this;
        }

        public Criteria andAuthorEqualTo(String value) {
            addCriterion("author =", value, "author");
            return (Criteria) this;
        }

        public Criteria andAuthorNotEqualTo(String value) {
            addCriterion("author <>", value, "author");
            return (Criteria) this;
        }

        public Criteria andAuthorGreaterThan(String value) {
            addCriterion("author >", value, "author");
            return (Criteria) this;
        }

        public Criteria andAuthorGreaterThanOrEqualTo(String value) {
            addCriterion("author >=", value, "author");
            return (Criteria) this;
        }

        public Criteria andAuthorLessThan(String value) {
            addCriterion("author <", value, "author");
            return (Criteria) this;
        }

        public Criteria andAuthorLessThanOrEqualTo(String value) {
            addCriterion("author <=", value, "author");
            return (Criteria) this;
        }

        public Criteria andAuthorLike(String value) {
            addCriterion("author like", value, "author");
            return (Criteria) this;
        }

        public Criteria andAuthorNotLike(String value) {
            addCriterion("author not like", value, "author");
            return (Criteria) this;
        }

        public Criteria andAuthorIn(List<String> values) {
            addCriterion("author in", values, "author");
            return (Criteria) this;
        }

        public Criteria andAuthorNotIn(List<String> values) {
            addCriterion("author not in", values, "author");
            return (Criteria) this;
        }

        public Criteria andAuthorBetween(String value1, String value2) {
            addCriterion("author between", value1, value2, "author");
            return (Criteria) this;
        }

        public Criteria andAuthorNotBetween(String value1, String value2) {
            addCriterion("author not between", value1, value2, "author");
            return (Criteria) this;
        }

        public Criteria andChapterWordSizeIsNull() {
            addCriterion("chapter_word_size is null");
            return (Criteria) this;
        }

        public Criteria andChapterWordSizeIsNotNull() {
            addCriterion("chapter_word_size is not null");
            return (Criteria) this;
        }

        public Criteria andChapterWordSizeEqualTo(Integer value) {
            addCriterion("chapter_word_size =", value, "chapterWordSize");
            return (Criteria) this;
        }

        public Criteria andChapterWordSizeNotEqualTo(Integer value) {
            addCriterion("chapter_word_size <>", value, "chapterWordSize");
            return (Criteria) this;
        }

        public Criteria andChapterWordSizeGreaterThan(Integer value) {
            addCriterion("chapter_word_size >", value, "chapterWordSize");
            return (Criteria) this;
        }

        public Criteria andChapterWordSizeGreaterThanOrEqualTo(Integer value) {
            addCriterion("chapter_word_size >=", value, "chapterWordSize");
            return (Criteria) this;
        }

        public Criteria andChapterWordSizeLessThan(Integer value) {
            addCriterion("chapter_word_size <", value, "chapterWordSize");
            return (Criteria) this;
        }

        public Criteria andChapterWordSizeLessThanOrEqualTo(Integer value) {
            addCriterion("chapter_word_size <=", value, "chapterWordSize");
            return (Criteria) this;
        }

        public Criteria andChapterWordSizeIn(List<Integer> values) {
            addCriterion("chapter_word_size in", values, "chapterWordSize");
            return (Criteria) this;
        }

        public Criteria andChapterWordSizeNotIn(List<Integer> values) {
            addCriterion("chapter_word_size not in", values, "chapterWordSize");
            return (Criteria) this;
        }

        public Criteria andChapterWordSizeBetween(Integer value1, Integer value2) {
            addCriterion("chapter_word_size between", value1, value2, "chapterWordSize");
            return (Criteria) this;
        }

        public Criteria andChapterWordSizeNotBetween(Integer value1, Integer value2) {
            addCriterion("chapter_word_size not between", value1, value2, "chapterWordSize");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNull() {
            addCriterion("update_time is null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNotNull() {
            addCriterion("update_time is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeEqualTo(Date value) {
            addCriterion("update_time =", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotEqualTo(Date value) {
            addCriterion("update_time <>", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThan(Date value) {
            addCriterion("update_time >", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("update_time >=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThan(Date value) {
            addCriterion("update_time <", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanOrEqualTo(Date value) {
            addCriterion("update_time <=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIn(List<Date> values) {
            addCriterion("update_time in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotIn(List<Date> values) {
            addCriterion("update_time not in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeBetween(Date value1, Date value2) {
            addCriterion("update_time between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotBetween(Date value1, Date value2) {
            addCriterion("update_time not between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andSourceUrlIsNull() {
            addCriterion("source_url is null");
            return (Criteria) this;
        }

        public Criteria andSourceUrlIsNotNull() {
            addCriterion("source_url is not null");
            return (Criteria) this;
        }

        public Criteria andSourceUrlEqualTo(String value) {
            addCriterion("source_url =", value, "sourceUrl");
            return (Criteria) this;
        }

        public Criteria andSourceUrlNotEqualTo(String value) {
            addCriterion("source_url <>", value, "sourceUrl");
            return (Criteria) this;
        }

        public Criteria andSourceUrlGreaterThan(String value) {
            addCriterion("source_url >", value, "sourceUrl");
            return (Criteria) this;
        }

        public Criteria andSourceUrlGreaterThanOrEqualTo(String value) {
            addCriterion("source_url >=", value, "sourceUrl");
            return (Criteria) this;
        }

        public Criteria andSourceUrlLessThan(String value) {
            addCriterion("source_url <", value, "sourceUrl");
            return (Criteria) this;
        }

        public Criteria andSourceUrlLessThanOrEqualTo(String value) {
            addCriterion("source_url <=", value, "sourceUrl");
            return (Criteria) this;
        }

        public Criteria andSourceUrlLike(String value) {
            addCriterion("source_url like", value, "sourceUrl");
            return (Criteria) this;
        }

        public Criteria andSourceUrlNotLike(String value) {
            addCriterion("source_url not like", value, "sourceUrl");
            return (Criteria) this;
        }

        public Criteria andSourceUrlIn(List<String> values) {
            addCriterion("source_url in", values, "sourceUrl");
            return (Criteria) this;
        }

        public Criteria andSourceUrlNotIn(List<String> values) {
            addCriterion("source_url not in", values, "sourceUrl");
            return (Criteria) this;
        }

        public Criteria andSourceUrlBetween(String value1, String value2) {
            addCriterion("source_url between", value1, value2, "sourceUrl");
            return (Criteria) this;
        }

        public Criteria andSourceUrlNotBetween(String value1, String value2) {
            addCriterion("source_url not between", value1, value2, "sourceUrl");
            return (Criteria) this;
        }

        public Criteria andChapterNoIsNull() {
            addCriterion("chapter_no is null");
            return (Criteria) this;
        }

        public Criteria andChapterNoIsNotNull() {
            addCriterion("chapter_no is not null");
            return (Criteria) this;
        }

        public Criteria andChapterNoEqualTo(Integer value) {
            addCriterion("chapter_no =", value, "chapterNo");
            return (Criteria) this;
        }

        public Criteria andChapterNoNotEqualTo(Integer value) {
            addCriterion("chapter_no <>", value, "chapterNo");
            return (Criteria) this;
        }

        public Criteria andChapterNoGreaterThan(Integer value) {
            addCriterion("chapter_no >", value, "chapterNo");
            return (Criteria) this;
        }

        public Criteria andChapterNoGreaterThanOrEqualTo(Integer value) {
            addCriterion("chapter_no >=", value, "chapterNo");
            return (Criteria) this;
        }

        public Criteria andChapterNoLessThan(Integer value) {
            addCriterion("chapter_no <", value, "chapterNo");
            return (Criteria) this;
        }

        public Criteria andChapterNoLessThanOrEqualTo(Integer value) {
            addCriterion("chapter_no <=", value, "chapterNo");
            return (Criteria) this;
        }

        public Criteria andChapterNoIn(List<Integer> values) {
            addCriterion("chapter_no in", values, "chapterNo");
            return (Criteria) this;
        }

        public Criteria andChapterNoNotIn(List<Integer> values) {
            addCriterion("chapter_no not in", values, "chapterNo");
            return (Criteria) this;
        }

        public Criteria andChapterNoBetween(Integer value1, Integer value2) {
            addCriterion("chapter_no between", value1, value2, "chapterNo");
            return (Criteria) this;
        }

        public Criteria andChapterNoNotBetween(Integer value1, Integer value2) {
            addCriterion("chapter_no not between", value1, value2, "chapterNo");
            return (Criteria) this;
        }

        public Criteria andChapterSequenceIsNull() {
            addCriterion("chapter_sequence is null");
            return (Criteria) this;
        }

        public Criteria andChapterSequenceIsNotNull() {
            addCriterion("chapter_sequence is not null");
            return (Criteria) this;
        }

        public Criteria andChapterSequenceEqualTo(String value) {
            addCriterion("chapter_sequence =", value, "chapterSequence");
            return (Criteria) this;
        }

        public Criteria andChapterSequenceNotEqualTo(String value) {
            addCriterion("chapter_sequence <>", value, "chapterSequence");
            return (Criteria) this;
        }

        public Criteria andChapterSequenceGreaterThan(String value) {
            addCriterion("chapter_sequence >", value, "chapterSequence");
            return (Criteria) this;
        }

        public Criteria andChapterSequenceGreaterThanOrEqualTo(String value) {
            addCriterion("chapter_sequence >=", value, "chapterSequence");
            return (Criteria) this;
        }

        public Criteria andChapterSequenceLessThan(String value) {
            addCriterion("chapter_sequence <", value, "chapterSequence");
            return (Criteria) this;
        }

        public Criteria andChapterSequenceLessThanOrEqualTo(String value) {
            addCriterion("chapter_sequence <=", value, "chapterSequence");
            return (Criteria) this;
        }

        public Criteria andChapterSequenceLike(String value) {
            addCriterion("chapter_sequence like", value, "chapterSequence");
            return (Criteria) this;
        }

        public Criteria andChapterSequenceNotLike(String value) {
            addCriterion("chapter_sequence not like", value, "chapterSequence");
            return (Criteria) this;
        }

        public Criteria andChapterSequenceIn(List<String> values) {
            addCriterion("chapter_sequence in", values, "chapterSequence");
            return (Criteria) this;
        }

        public Criteria andChapterSequenceNotIn(List<String> values) {
            addCriterion("chapter_sequence not in", values, "chapterSequence");
            return (Criteria) this;
        }

        public Criteria andChapterSequenceBetween(String value1, String value2) {
            addCriterion("chapter_sequence between", value1, value2, "chapterSequence");
            return (Criteria) this;
        }

        public Criteria andChapterSequenceNotBetween(String value1, String value2) {
            addCriterion("chapter_sequence not between", value1, value2, "chapterSequence");
            return (Criteria) this;
        }

        public Criteria andChapterArticleIsNull() {
            addCriterion("chapter_article is null");
            return (Criteria) this;
        }

        public Criteria andChapterArticleIsNotNull() {
            addCriterion("chapter_article is not null");
            return (Criteria) this;
        }

        public Criteria andChapterArticleEqualTo(String value) {
            addCriterion("chapter_article =", value, "chapterArticle");
            return (Criteria) this;
        }

        public Criteria andChapterArticleNotEqualTo(String value) {
            addCriterion("chapter_article <>", value, "chapterArticle");
            return (Criteria) this;
        }

        public Criteria andChapterArticleGreaterThan(String value) {
            addCriterion("chapter_article >", value, "chapterArticle");
            return (Criteria) this;
        }

        public Criteria andChapterArticleGreaterThanOrEqualTo(String value) {
            addCriterion("chapter_article >=", value, "chapterArticle");
            return (Criteria) this;
        }

        public Criteria andChapterArticleLessThan(String value) {
            addCriterion("chapter_article <", value, "chapterArticle");
            return (Criteria) this;
        }

        public Criteria andChapterArticleLessThanOrEqualTo(String value) {
            addCriterion("chapter_article <=", value, "chapterArticle");
            return (Criteria) this;
        }

        public Criteria andChapterArticleLike(String value) {
            addCriterion("chapter_article like", value, "chapterArticle");
            return (Criteria) this;
        }

        public Criteria andChapterArticleNotLike(String value) {
            addCriterion("chapter_article not like", value, "chapterArticle");
            return (Criteria) this;
        }

        public Criteria andChapterArticleIn(List<String> values) {
            addCriterion("chapter_article in", values, "chapterArticle");
            return (Criteria) this;
        }

        public Criteria andChapterArticleNotIn(List<String> values) {
            addCriterion("chapter_article not in", values, "chapterArticle");
            return (Criteria) this;
        }

        public Criteria andChapterArticleBetween(String value1, String value2) {
            addCriterion("chapter_article between", value1, value2, "chapterArticle");
            return (Criteria) this;
        }

        public Criteria andChapterArticleNotBetween(String value1, String value2) {
            addCriterion("chapter_article not between", value1, value2, "chapterArticle");
            return (Criteria) this;
        }

        public Criteria andChapterNameIsNull() {
            addCriterion("chapter_name is null");
            return (Criteria) this;
        }

        public Criteria andChapterNameIsNotNull() {
            addCriterion("chapter_name is not null");
            return (Criteria) this;
        }

        public Criteria andChapterNameEqualTo(String value) {
            addCriterion("chapter_name =", value, "chapterName");
            return (Criteria) this;
        }

        public Criteria andChapterNameNotEqualTo(String value) {
            addCriterion("chapter_name <>", value, "chapterName");
            return (Criteria) this;
        }

        public Criteria andChapterNameGreaterThan(String value) {
            addCriterion("chapter_name >", value, "chapterName");
            return (Criteria) this;
        }

        public Criteria andChapterNameGreaterThanOrEqualTo(String value) {
            addCriterion("chapter_name >=", value, "chapterName");
            return (Criteria) this;
        }

        public Criteria andChapterNameLessThan(String value) {
            addCriterion("chapter_name <", value, "chapterName");
            return (Criteria) this;
        }

        public Criteria andChapterNameLessThanOrEqualTo(String value) {
            addCriterion("chapter_name <=", value, "chapterName");
            return (Criteria) this;
        }

        public Criteria andChapterNameLike(String value) {
            addCriterion("chapter_name like", value, "chapterName");
            return (Criteria) this;
        }

        public Criteria andChapterNameNotLike(String value) {
            addCriterion("chapter_name not like", value, "chapterName");
            return (Criteria) this;
        }

        public Criteria andChapterNameIn(List<String> values) {
            addCriterion("chapter_name in", values, "chapterName");
            return (Criteria) this;
        }

        public Criteria andChapterNameNotIn(List<String> values) {
            addCriterion("chapter_name not in", values, "chapterName");
            return (Criteria) this;
        }

        public Criteria andChapterNameBetween(String value1, String value2) {
            addCriterion("chapter_name between", value1, value2, "chapterName");
            return (Criteria) this;
        }

        public Criteria andChapterNameNotBetween(String value1, String value2) {
            addCriterion("chapter_name not between", value1, value2, "chapterName");
            return (Criteria) this;
        }

        public Criteria andVolumeNameIsNull() {
            addCriterion("volume_name is null");
            return (Criteria) this;
        }

        public Criteria andVolumeNameIsNotNull() {
            addCriterion("volume_name is not null");
            return (Criteria) this;
        }

        public Criteria andVolumeNameEqualTo(String value) {
            addCriterion("volume_name =", value, "volumeName");
            return (Criteria) this;
        }

        public Criteria andVolumeNameNotEqualTo(String value) {
            addCriterion("volume_name <>", value, "volumeName");
            return (Criteria) this;
        }

        public Criteria andVolumeNameGreaterThan(String value) {
            addCriterion("volume_name >", value, "volumeName");
            return (Criteria) this;
        }

        public Criteria andVolumeNameGreaterThanOrEqualTo(String value) {
            addCriterion("volume_name >=", value, "volumeName");
            return (Criteria) this;
        }

        public Criteria andVolumeNameLessThan(String value) {
            addCriterion("volume_name <", value, "volumeName");
            return (Criteria) this;
        }

        public Criteria andVolumeNameLessThanOrEqualTo(String value) {
            addCriterion("volume_name <=", value, "volumeName");
            return (Criteria) this;
        }

        public Criteria andVolumeNameLike(String value) {
            addCriterion("volume_name like", value, "volumeName");
            return (Criteria) this;
        }

        public Criteria andVolumeNameNotLike(String value) {
            addCriterion("volume_name not like", value, "volumeName");
            return (Criteria) this;
        }

        public Criteria andVolumeNameIn(List<String> values) {
            addCriterion("volume_name in", values, "volumeName");
            return (Criteria) this;
        }

        public Criteria andVolumeNameNotIn(List<String> values) {
            addCriterion("volume_name not in", values, "volumeName");
            return (Criteria) this;
        }

        public Criteria andVolumeNameBetween(String value1, String value2) {
            addCriterion("volume_name between", value1, value2, "volumeName");
            return (Criteria) this;
        }

        public Criteria andVolumeNameNotBetween(String value1, String value2) {
            addCriterion("volume_name not between", value1, value2, "volumeName");
            return (Criteria) this;
        }

        public Criteria andGmtCreateIsNull() {
            addCriterion("gmt_create is null");
            return (Criteria) this;
        }

        public Criteria andGmtCreateIsNotNull() {
            addCriterion("gmt_create is not null");
            return (Criteria) this;
        }

        public Criteria andGmtCreateEqualTo(Date value) {
            addCriterion("gmt_create =", value, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateNotEqualTo(Date value) {
            addCriterion("gmt_create <>", value, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateGreaterThan(Date value) {
            addCriterion("gmt_create >", value, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateGreaterThanOrEqualTo(Date value) {
            addCriterion("gmt_create >=", value, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateLessThan(Date value) {
            addCriterion("gmt_create <", value, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateLessThanOrEqualTo(Date value) {
            addCriterion("gmt_create <=", value, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateIn(List<Date> values) {
            addCriterion("gmt_create in", values, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateNotIn(List<Date> values) {
            addCriterion("gmt_create not in", values, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateBetween(Date value1, Date value2) {
            addCriterion("gmt_create between", value1, value2, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateNotBetween(Date value1, Date value2) {
            addCriterion("gmt_create not between", value1, value2, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedIsNull() {
            addCriterion("gmt_modified is null");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedIsNotNull() {
            addCriterion("gmt_modified is not null");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedEqualTo(Date value) {
            addCriterion("gmt_modified =", value, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedNotEqualTo(Date value) {
            addCriterion("gmt_modified <>", value, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedGreaterThan(Date value) {
            addCriterion("gmt_modified >", value, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedGreaterThanOrEqualTo(Date value) {
            addCriterion("gmt_modified >=", value, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedLessThan(Date value) {
            addCriterion("gmt_modified <", value, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedLessThanOrEqualTo(Date value) {
            addCriterion("gmt_modified <=", value, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedIn(List<Date> values) {
            addCriterion("gmt_modified in", values, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedNotIn(List<Date> values) {
            addCriterion("gmt_modified not in", values, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedBetween(Date value1, Date value2) {
            addCriterion("gmt_modified between", value1, value2, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedNotBetween(Date value1, Date value2) {
            addCriterion("gmt_modified not between", value1, value2, "gmtModified");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}