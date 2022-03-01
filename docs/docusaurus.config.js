// @ts-check
// Note: type annotations allow type checking and IDEs autocompletion

const lightCodeTheme = require('prism-react-renderer/themes/github');
const darkCodeTheme = require('prism-react-renderer/themes/dracula');

/** @type {import('@docusaurus/types').Config} */
const config = {
  title: 'Thinkrchive',
  tagline: 'A simple app showing details for various Lenovo Thinkpad models. Made to try out Jepack Compose for Android and Desktop. It uses Kotlin Multiplatform to share code.',
  url: 'https://Thinkrchive.github.io',
  baseUrl: '/Thinkrchive-Multiplatform/',
  onBrokenLinks: 'throw',
  onBrokenMarkdownLinks: 'warn',
  favicon: 'img/thinkrchive.svg',
  organizationName: 'Thinkrchive', // Usually your GitHub org/user name.
  projectName: 'Thinkrchive-Multiplatform', // Usually your repo name.
  deploymentBranch: 'main',
  trailingSlash: false,

  presets: [
    [
      'classic',
      /** @type {import('@docusaurus/preset-classic').Options} */
      ({
        docs: {
          routeBasePath: '/',
          sidebarPath: require.resolve('./sidebars.js'),
          // Please change this to your repo.
          editUrl: 'https://github.com/facebook/docusaurus/tree/main/packages/create-docusaurus/templates/shared/',
        },
        blog: false,
        theme: {
          customCss: require.resolve('./src/css/custom.css'),
        },
      }),
    ],
  ],

  themeConfig:
    /** @type {import('@docusaurus/preset-classic').ThemeConfig} */
    ({
      navbar: {
        title: 'Thinkrchive',
        logo: {
          alt: 'Thinkrchive Logo',
          src: 'img/thinkrchive.svg',
        },
        items: [
          {
            type: 'doc',
            docId: 'overview/briefing',
            position: 'left',
            label: 'Overview',
          },
          {
            type: 'doc',
            docId: 'architecture/intro',
            position: 'left',
            label: 'Architecture',
          },
          //{to: '/blog', label: 'Blog', position: 'left'},
          {
            href: 'https://github.com/Thinkrchive',
            label: 'GitHub',
            position: 'right',
          },
        ],
      },
      footer: {
        style: 'dark',
        links: [
          {
            title: 'Docs',
            items: [
              {
                label: 'Overview',
                to: '/overview/briefing',
              },
              {
                label: 'Architecture',
                to: '/architecture/intro',
              },
            ],
          },
          {
            title: 'Contact',
            items: [
              {
                label: 'Email Me',
                href: 'mailto:bmkilaha@gmail.com',
              },
              {
                label: 'Twitter',
                href: 'https://twitter.com/rackadev',
              },
              {
                label: 'Telegram',
                href: 'https://t.me/Racka98',
              },
            ],
          },
          {
            title: 'More',
            items: [
              // {
              //   label: 'Blog',
              //   to: '/blog',
              // },
              {
                label: 'Thinkrchive Github Org',
                href: 'https://github.com/Thinkrchive',
              },
              {
                label: 'Multiplatform Project Repo',
                href: 'https://github.com/racka98/Thinkrchive-Multiplatform',
              },
            ],
          },
        ],
        copyright: `Copyright © ${new Date().getFullYear()} Thinkrchive Multiplatform. Built with Docusaurus.`,
      },
      prism: {
        theme: lightCodeTheme,
        darkTheme: darkCodeTheme,
      },
    }),
};

module.exports = config;
