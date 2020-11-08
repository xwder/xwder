package com.xwder.app.modules.user.entity;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
/**
 * QQ用户信息表
 *
 * @Author xwder
 * @Date 2020年11月08日16:40:25
 */
@Entity
@Table ( name ="app_user_github" )
@EntityListeners(AuditingEntityListener.class)
public class UserGithub  implements Serializable {

	private static final long serialVersionUID =  5173471197902132962L;

    /**
     * id 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
	private Long id;

	/**
	 * 登录用户名
	 */
   	@Column(name = "login" )
	private String login;

	/**
	 * gitbug_id字段 用户编号
	 */
   	@Column(name = "github_id" )
	private Long githubId;

   	@Column(name = "node_id" )
	private String nodeId;

	/**
	 * 头像url
	 */
   	@Column(name = "avatar_url" )
	private String avatarUrl;

   	@Column(name = "gravatar_id" )
	private String gravatarId;

   	@Column(name = "url" )
	private String url;

   	@Column(name = "html_url" )
	private String htmlUrl;

   	@Column(name = "followers_url" )
	private String followersUrl;

   	@Column(name = "following_url" )
	private String followingUrl;

   	@Column(name = "gists_url" )
	private String gistsUrl;

   	@Column(name = "starred_url" )
	private String starredUrl;

   	@Column(name = "subscriptions_url" )
	private String subscriptionsUrl;

   	@Column(name = "organizations_url" )
	private String organizationsUrl;

   	@Column(name = "repos_url" )
	private String reposUrl;

   	@Column(name = "events_url" )
	private String eventsUrl;

   	@Column(name = "received_events_url" )
	private String receivedEventsUrl;

   	@Column(name = "type" )
	private String type;

   	@Column(name = "site_admin" )
	private String siteAdmin;

   	@Column(name = "name" )
	private String name;

   	@Column(name = "company" )
	private String company;

   	@Column(name = "blog" )
	private String blog;

   	@Column(name = "location" )
	private String location;

   	@Column(name = "email" )
	private String email;

   	@Column(name = "hireable" )
	private String hireable;

   	@Column(name = "bio" )
	private String bio;

   	@Column(name = "twitter_username" )
	private String twitterUsername;

   	@Column(name = "public_repos" )
	private Long publicRepos;

   	@Column(name = "public_gists" )
	private Long publicGists;

   	@Column(name = "followers" )
	private Long followers;

   	@Column(name = "following" )
	private Long following;

   	@Column(name = "created_at" )
	private Date createdAt;

   	@Column(name = "updated_at" )
	private Date updatedAt;

	/**
	 * 创建时间
	 */
   	@Column(name = "gmt_create" )
	private Date gmtCreate;

	/**
	 * 修改时间
	 */
   	@Column(name = "gmt_modified" )
	private Date gmtModified;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }


  public String getLogin() {
    return login;
  }

  public void setLogin(String login) {
    this.login = login;
  }


  public Long getGithubId() {
    return githubId;
  }

  public void setGithubId(Long githubId) {
    this.githubId = githubId;
  }


  public String getNodeId() {
    return nodeId;
  }

  public void setNodeId(String nodeId) {
    this.nodeId = nodeId;
  }


  public String getAvatarUrl() {
    return avatarUrl;
  }

  public void setAvatarUrl(String avatarUrl) {
    this.avatarUrl = avatarUrl;
  }


  public String getGravatarId() {
    return gravatarId;
  }

  public void setGravatarId(String gravatarId) {
    this.gravatarId = gravatarId;
  }


  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }


  public String getHtmlUrl() {
    return htmlUrl;
  }

  public void setHtmlUrl(String htmlUrl) {
    this.htmlUrl = htmlUrl;
  }


  public String getFollowersUrl() {
    return followersUrl;
  }

  public void setFollowersUrl(String followersUrl) {
    this.followersUrl = followersUrl;
  }


  public String getFollowingUrl() {
    return followingUrl;
  }

  public void setFollowingUrl(String followingUrl) {
    this.followingUrl = followingUrl;
  }


  public String getGistsUrl() {
    return gistsUrl;
  }

  public void setGistsUrl(String gistsUrl) {
    this.gistsUrl = gistsUrl;
  }


  public String getStarredUrl() {
    return starredUrl;
  }

  public void setStarredUrl(String starredUrl) {
    this.starredUrl = starredUrl;
  }


  public String getSubscriptionsUrl() {
    return subscriptionsUrl;
  }

  public void setSubscriptionsUrl(String subscriptionsUrl) {
    this.subscriptionsUrl = subscriptionsUrl;
  }


  public String getOrganizationsUrl() {
    return organizationsUrl;
  }

  public void setOrganizationsUrl(String organizationsUrl) {
    this.organizationsUrl = organizationsUrl;
  }


  public String getReposUrl() {
    return reposUrl;
  }

  public void setReposUrl(String reposUrl) {
    this.reposUrl = reposUrl;
  }


  public String getEventsUrl() {
    return eventsUrl;
  }

  public void setEventsUrl(String eventsUrl) {
    this.eventsUrl = eventsUrl;
  }


  public String getReceivedEventsUrl() {
    return receivedEventsUrl;
  }

  public void setReceivedEventsUrl(String receivedEventsUrl) {
    this.receivedEventsUrl = receivedEventsUrl;
  }


  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }


  public String getSiteAdmin() {
    return siteAdmin;
  }

  public void setSiteAdmin(String siteAdmin) {
    this.siteAdmin = siteAdmin;
  }


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }


  public String getCompany() {
    return company;
  }

  public void setCompany(String company) {
    this.company = company;
  }


  public String getBlog() {
    return blog;
  }

  public void setBlog(String blog) {
    this.blog = blog;
  }


  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }


  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }


  public String getHireable() {
    return hireable;
  }

  public void setHireable(String hireable) {
    this.hireable = hireable;
  }


  public String getBio() {
    return bio;
  }

  public void setBio(String bio) {
    this.bio = bio;
  }


  public String getTwitterUsername() {
    return twitterUsername;
  }

  public void setTwitterUsername(String twitterUsername) {
    this.twitterUsername = twitterUsername;
  }


  public Long getPublicRepos() {
    return publicRepos;
  }

  public void setPublicRepos(Long publicRepos) {
    this.publicRepos = publicRepos;
  }


  public Long getPublicGists() {
    return publicGists;
  }

  public void setPublicGists(Long publicGists) {
    this.publicGists = publicGists;
  }


  public Long getFollowers() {
    return followers;
  }

  public void setFollowers(Long followers) {
    this.followers = followers;
  }


  public Long getFollowing() {
    return following;
  }

  public void setFollowing(Long following) {
    this.following = following;
  }


  public Date getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }


  public Date getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(Date updatedAt) {
    this.updatedAt = updatedAt;
  }


  public Date getGmtCreate() {
    return gmtCreate;
  }

  public void setGmtCreate(Date gmtCreate) {
    this.gmtCreate = gmtCreate;
  }


  public Date getGmtModified() {
    return gmtModified;
  }

  public void setGmtModified(Date gmtModified) {
    this.gmtModified = gmtModified;
  }

}
