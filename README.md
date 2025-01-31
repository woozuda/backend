# 우주다 BackEnd
<p align="center">
  <a href="https://woozuda.swygbro.com/">
      <img src="https://github.com/user-attachments/assets/25ec575a-9f36-4b6a-9a9b-a4433d281973" width="100" />
    </a>
</p>
<br>

## 프로젝트 소개
### 편하게 일기 쓰고 기록을 공유하는 내 손안의 디지털 AI 다이어리 우주다

MZ 세대는 블로그를 통해 일기를 작성하는 경향이 있습니다. 블로그를 통한 일기 작성은 단순한 개인적인 기록을 넘어서, 사람들과의 공유 공간으로 활용되기도 합니다. 
기존의 일기 서비스들이 기록의 도구에만 집중해온 반면, 우주다는 편리한 일기 도구이자 지인들과 기록을 쉽게 공유할 수 있는 서비스로 기획되었습니다

<br>

## 팀원

<table align="center">
  <tr>
    <th><a href="https://github.com/seop-h">황중섭 (백엔드 팀장)</a></th>
    <th><a href="https://github.com/rodom1018">이동현 (백엔드 팀원)</a></th>
    <th><a href="https://github.com/baesaa0304">배선영 (백엔드 팀원)</a></th>
  </tr>
  <tr>
    <td>- 인프라 구축 및 설계 <br> - 다이어리 기능 <br> - 일기 및 회고 생성 및 관리</td>
    <td>- 회원 관리 <br> - OAuth2 로그인 <br> - 이미지 저장 <br> - 알림 기능 <br> - 숏링크 생성 및 관리</td>
    <td>- AI 기반 일기와 회고 분석 리포트 제공 <br> - 창작 콘텐츠 생성 및 관리</td>
  </tr>
</table>

<br>

## 기술 스택
#### Language
- Java

#### Framework & Server
- Spring
- Spring Security
- NCloud Server
- GitHub Actions

#### DB
- MySQL 

#### IDE
- IntelliJ

#### Documentation
- Swaager
- Notion
- ERD Cloud 

#### API
- Open Ai
- NCloud Object Storage

#### Monitoring
- Prometheus
- Loki Stack
<br>
<p align="center">
  <img src="https://github.com/user-attachments/assets/d3319c54-633b-4661-be74-d6cd3c6f054d" width="700" />
</p>
<br>

## 협업 기준
#### 브랜치 전략
  - Git Workflow를 베이스으로 프로젝트에 맞게 변형
  - main -> release -> develop -> feature/기능 -> fix/기능
  - main: 실제 운영 환경 (product), release: 운영 환경과 유사한 테스트 환경 (QA)

#### Pull Request
- 기능 단위별로 PR 요청
- PR 요청이 올라오면 다른 두 명의 백엔드 팀원이 코드 리뷰
- 둘 모두의 승인을 받은 후 PR merge
- 항상 모든 팀원들은 적극적으로 코드 리뷰를 하고, 합쳐진 브랜치의 변경 사항을 당겨와 최신 상태 유지 및 PR 시 충돌 최소화

<br>

이외에도 원활한 의사소통을 위해 노력 그리고 진행 상황을 중간중간 명확히 공유.

<br>

## ERD
<p align="center">
  <img src="https://github.com/user-attachments/assets/1a659fbe-f291-4686-99d5-fdd55afa959d" width="700"/>
</p>
<br>

## 프로젝트 아키텍처
<p align="center">
  <img src="https://github.com/user-attachments/assets/95e1cc6a-97c8-4d75-ad27-0595ffaf398c" width="700"/>
</p>

