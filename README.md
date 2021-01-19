# skin_Check

대학교 졸업작품으로 만든 스킨체크 어플입니다.

dlib 라이브러리를 이용해서 사람 얼굴을 인식하고 유저가 제공한 사진에서 얼굴만 잘라냅니다.<br/>
그리고 얼굴사진을 두장으로 복사해서 <br/>
한장에 사진에는 블러처리를 하여 피부의 잡티를 전부 지우는 동작을 실행하고<br/>
원본 사진과 픽셀을 비교합니다.<br/>
그리고 그 사이의 수치로 얼굴의 잡티 , 모공 ,상처가 몇퍼센트인지 분석하고<br/>
피부 상태가 어떤지 진단합니다.<br/>

서버 : node.js<br/>
얼굴 분석 : python , openCv , dlib 라이브러리<br/>
클라이언트 : Android(Java)<br/>
DB : Mysql



시연영상 링크 : https://www.youtube.com/watch?v=icH-UMit3OY&t=2s
