Arboreum is a stripped-down Content Management System (CMS). The core concept is that all posts are arranged in a hierarchy of nestable categories, leading to a natural navigation system and URL scheme.

There are four webpage-type resources in Arboreum:
 - **Home Page:** The page that the user sees when they visit the root of your website. There is only one of these in a site.
 - **Category Page:** Front page for a category of posts. By default, has a description text block, and  subcategories and attached posts. Styled as a group.
 - **Post:** Blog post written with Markdown. May have comments enabled. Styled as a group.
 - **Static:** Individually styled static page.

# Status
Currently, there is no Arboreum. A careful analysis of the codebase will reveal that none yet exists. This is just an initial commit.

# Roadmap
 - **0.0.1:** Add admin console, store data, no content serving yet.
 - **Thereafter:** Everything else promised above, hopefully.

# FAQ
At the time of this writing, literally nobody knows about this project except me (and I do mean *literally*, I haven't even put it online yet, although if you're reading this I presumably have by now), so I haven't yet received any questions, frequent or otherwise, but here are some I imagine might be asked.

## Q: ...why?
### SubQ: Why not just use `${pick one from setOf(Wordpress, Drupal, ETC)}`?
**A:** Mostly because I thought this could be fun to make. Also, I've used Wordpress some, and I've always found it a little needlessly complicated for the things I wanted to do. I thought I might try my hand at a greatly simplified (although less general) type of CMS. Time will tell if it proves to be useful, and also if I prove to ever complete it to a useful degree.
## Q: Is this a replacement for `${pick one from setOf(Wordpress, Drupal, ETC)}`?
**A:** Not really. Firstly, it doesn't exist yet. Secondly, it will never have close to the number of features those CMSes have unless it for some reason gets adopted a ton of people and the development community grows, or in other words, a development community arises where none currently exists. It's meant to fulfill the specific purpose of organizing posts hierarchically in the simplest manner possible. It will ideally one day be able to  serve as a replacement those systems in that exact scenario, but it doesn't aim to ever be as generalizable.
## Q: Is this production-ready?
God, no. If you've read anything above, you'll know it doesn't exist yet. Furthermore, even though I plan to try to follow security best practices, I'm not enough of an expert in that sector to guarantee it's solid. Same goes for performance. Lastly, it will be a while before it's feature-rich enough for me to imagine it could be useful to anyone except me, although feel free to give it a try if you're interested.