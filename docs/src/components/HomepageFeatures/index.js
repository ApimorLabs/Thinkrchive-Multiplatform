import React from 'react';
import clsx from 'clsx';
import styles from './styles.module.css';

const FeatureList = [
  {
    title: 'Kotlin Multiplatform',
    Svg: require('@site/static/img/kmm.svg').default,
    description: (
      <>
        This app shares most of it&apos;s business logic between Android and Desktop using Kotlin Multiplatform. 
        You can easily add support for other supported platforms like iOS, Web, WearOS, watchOS, etc.
      </>
    ),
  },
  {
    title: 'Compose for UI',
    Svg: require('@site/static/img/compose.svg').default,
    description: (
      <>
        UI for Android is made using Jetpack Compose from Google while UI for Desktop is made 
        using Compose Multiplatform. Compose Multiplatform can also be used with Android so you 
        can have a single codebase for UI.
        
      </>
    ),
  },
  {
    title: 'Ktor Powered Backend',
    Svg: require('@site/static/img/ktor.svg').default,
    description: (
      <>
        The server backend that serves the list is a simple JSON API made using Ktor. It is hosted on 
        Heroku and has features like JWT Authentication for admins, error responses, Postgresql database connection, 
        Dependency Injection, etc.
      </>
    ),
  },
];

function Feature({Svg, title, description}) {
  return (
    <div className={clsx('col col--4')}>
      <div className="text--center">
        <Svg className={styles.featureSvg} alt={title} />
      </div>
      <div className="text--center padding-horiz--md">
        <h3>{title}</h3>
        <p>{description}</p>
      </div>
    </div>
  );
}

export default function HomepageFeatures() {
  return (
    <section className={styles.features}>
      <div className="container">
        <div className="row">
          {FeatureList.map((props, idx) => (
            <Feature key={idx} {...props} />
          ))}
        </div>
      </div>
    </section>
  );
}
